/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetpack.viewmodel.source;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 1、主要用于处理UI的数据和一些业务逻辑，不要持有activity或fragment，通过LiveData或者其他通知组件交互
 * 2、可以作为一个Activity中不同Fragment之间交互的桥梁.
 * <pre>
 * public class MyFragment extends Fragment {
 *     public void onStart() {
 *         UserModel userModel = ViewModelProviders.of(getActivity()).get(UserModel.class);
 *     }
 * }
 * </pre>
 */
public abstract class ViewModel {
    @Nullable
    private final Map<String, Object> mBagOfTags = new HashMap<>();
    private volatile boolean mCleared = false;


    protected void onCleared() {
    }

    //ViewModelStore.clear----ViewModel.clear---onCleared()
    @MainThread
    final void clear() {
        mCleared = true;
        if (mBagOfTags != null) {
            synchronized (mBagOfTags) {
                for (Object value : mBagOfTags.values()) {
                    closeWithRuntimeException(value);
                }
            }
        }
        onCleared();
    }


    <T> T setTagIfAbsent(String key, T newValue) {
        T previous;
        synchronized (mBagOfTags) {
            previous = (T) mBagOfTags.get(key);
            if (previous == null) {
                mBagOfTags.put(key, newValue);
            }
        }
        T result = previous == null ? newValue : previous;
        if (mCleared) {
            closeWithRuntimeException(result);
        }
        return result;
    }


    <T> T getTag(String key) {
        synchronized (mBagOfTags) {
            return (T) mBagOfTags.get(key);
        }
    }

    private static void closeWithRuntimeException(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
