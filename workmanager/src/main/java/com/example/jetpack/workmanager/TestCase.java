package com.example.jetpack.workmanager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangquan
 */
public class TestCase extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 调度一次性工作
     */
    private void oneTimeWork() {
        OneTimeWorkRequest workRequest = null;

        //对于无需额外配置的简单工作
        workRequest = OneTimeWorkRequest.from(UploadWorker.class);

        //对于复杂的工作，可以使用构建器。
        workRequest =
                new OneTimeWorkRequest.Builder(UploadWorker.class)
                        //添加约束  .setConstraints(getConstraint())
                        //延迟执行 .setInitialDelay(10, TimeUnit.MINUTES) 在加入队列后至少经过 10 分钟后再运行。
                        .build();


        WorkManager.getInstance(this)
                .enqueue(workRequest);
    }

    /**
     * 调度定期工作
     */
    private void periodicWork() {
        PeriodicWorkRequest workRequest = null;

        /**
         * 最短重复间隔是 15 分钟（与 JobScheduler API 相同）。
         * 确切执行时间取决于WorkRequest 对象中设置的约束以及系统执行的优化。
         */
        workRequest = new PeriodicWorkRequest.Builder(UploadWorker.class, 15, TimeUnit.MINUTES)
                //约束条件
                /**
                 * 约束对定期工作的影响
                 * 您可以对定期工作设置约束。例如，您可以为工作请求添加约束，以便工作仅在用户设备充电时运行。
                 * 在这种情况下，除非满足约束条件，否则即使过了定义的重复间隔，PeriodicWorkRequest 也不会运行。
                 * 这可能会导致工作在某次运行时出现延迟，甚至会因在相应间隔内未满足条件而被跳过。
                 */
                .build();


        WorkManager.getInstance(this)
                .enqueue(workRequest);
    }

    /**
     * 约束条件
     *
     * @return
     */
    private Constraints getConstraint() {
        /**
         * NetworkType	约束运行工作所需的网络类型。例如 Wi-Fi (UNMETERED)。
         * BatteryNotLow	如果设置为 true，那么当设备处于“电量不足模式”时，工作不会运行。
         * RequiresCharging	如果设置为 true，那么工作只能在设备充电时运行。
         * DeviceIdle	如果设置为 true，则要求用户的设备必须处于空闲状态，才能运行工作。如果您要运行批量操作，否则可能会降低用户设备上正在积极运行的其他应用的性能，建议您使用此约束。
         * StorageNotLow	如果设置为 true，那么当用户设备上的存储空间不足时，工作不会运行。
         */

        //比如仅在用户设备正在充电且连接到 Wi-Fi 网络时才会运行
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .build();
        /**
         * 如果指定了多个约束，工作将仅在满足所有约束时才会运行。
         * 如果在工作运行时不再满足某个约束，WorkManager 将停止工作器。系统将在满足所有约束后重试工作。所以请谨慎添加约束条件
         */
        return constraints;
    }

    /**
     * 重试和退避策略
     */
    private void retryAndBackoff(){
        /**
         * worker返回 Result.retry()。然后，系统将根据退避延迟时间和退避政策重新调度工作。
         * 退避延迟时间指定了首次尝试后重试工作前的最短等待时间。此值不能超过 10 秒（或 MIN_BACKOFF_MILLIS）。
         * 退避政策定义了在后续重试过程中，退避延迟时间随时间以怎样的方式增长。WorkManager 支持 2 个退避政策，即 LINEAR 和 EXPONENTIAL。
         */

        WorkRequest myWorkRequest =
                new OneTimeWorkRequest.Builder(UploadWorker.class)
                        .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.MILLISECONDS)
                        .build();

        /**
         * 在本示例中，最短退避延迟时间设置为允许的最小值，即 10 秒。
         * 由于政策为 LINEAR，每次尝试重试时，重试间隔都会增加约 10 秒。例如，第一次运行以 Result.retry() 结束并在 10 秒后重试；
         * 然后，如果继续返回 Result.retry()，那么接下来会在 20 秒、30 秒、40 秒后重试，以此类推。
         * 如果退避政策设置为 EXPONENTIAL，那么重试时长序列将接近 20、40、80 秒，以此类推。
         */
    }

    private void addTag(){
        String tag="upload";
        WorkRequest myWorkRequest =
                new OneTimeWorkRequest.Builder(UploadWorker.class)
                        .addTag(tag)
                        //可以添加多个tag
                        .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(myWorkRequest);

        //
        ListenableFuture<List<WorkInfo>> workInfosByTag = workManager.getWorkInfosByTag(tag);
        workInfosByTag.isDone();
        workInfosByTag.isCancelled();
        workInfosByTag.cancel(true);

        //取消带有特定标记的所有工作请求
       workManager.cancelAllWorkByTag(tag);

    }

    /**
     *  输入数据 WorkRequest.Builder().setInputData(input)
     *  返回数据 Result.success(output);
     */
    private void inputOutData(){
        Data inputData = new Data.Builder()
                .putInt("key1", 42)
                .putString("key2","xx")
                .build();

        OneTimeWorkRequest mathWork = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(inputData)
                .build();
        WorkManager.getInstance(this).enqueue(mathWork);

        //返回数据 Result.success(output);

/*        public class MathWorker extends Worker {

            public MathWorker(
                    @NonNull Context context,
                    @NonNull WorkerParameters params) {
                super(context, params);
            }

            @Override
            public Result doWork() {
                Data inputData1 = getInputData();

                //返回数据
                Data output = new Data.Builder()
                        .putInt("KEY_RESULT", 1)
                        .build();
                return Result.success(output);
            }
        }*/

    }
}
