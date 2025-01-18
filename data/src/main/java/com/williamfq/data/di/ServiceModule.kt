package com.williamfq.data.di

import com.williamfq.data.services.AlertService
import com.williamfq.data.repository.AlertServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAlertService(alertServiceImpl: AlertServiceImpl): AlertService {
        return alertServiceImpl
    }
}
