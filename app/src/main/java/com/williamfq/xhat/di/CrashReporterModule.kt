// src/main/java/com/williamfq/xhat/di/CrashReporterModule.kt

package com.williamfq.xhat.di

import com.williamfq.xhat.utils.CrashReporter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CrashReporterModule {

    @Provides
    @Singleton
    fun provideCrashReporter(): CrashReporter {
        return CrashReporter()
    }
}