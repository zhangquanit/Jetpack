package com.example.jetpack.viewbinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetpack.viewbinding.databinding.ActivityMainBinding;
import com.example.jetpack.viewbinding.databinding.ItemFragBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_main); 还是可以使用
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvLabel.setTextColor(Color.RED);
        binding.btnAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container, new ItemFrag()).commit();
            }
        });
    }

    public static class ItemFrag extends Fragment {
        ItemFragBinding fragBinding;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            fragBinding = ItemFragBinding.inflate(inflater, container, false);
            fragBinding.tvLabel.setTextColor(Color.GREEN);
            return fragBinding.getRoot();
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            // 注意：Fragment 的存在时间比其视图长。请务必在 Fragment 的 onDestroyView() 方法中清除对绑定类实例的所有引用。
            fragBinding = null;
        }
    }
}