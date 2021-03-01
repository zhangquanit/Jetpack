package com.example.hilt.app

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 *
 * @author zhangquan
 */
@Module
@InstallIn(ActivityComponent::class) //在activity中使用
abstract class AnalyticsModule {
    @Binds
    abstract fun bindAnalyticsService(analyticsServiceImpl: AnalyticsServiceImpl): AnalyticsService
}