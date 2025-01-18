package com.williamfq.data.di

import com.williamfq.data.repository.*
import com.williamfq.domain.repository.*
import com.williamfq.domain.repository.UserActivityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Bind Repositories
    @Binds
    @Singleton
    abstract fun bindCommunityRepository(
        communityRepositoryImpl: CommunityRepositoryImpl
    ): CommunityRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun bindPanicRepository(
        panicRepositoryImpl: PanicRepositoryImpl
    ): PanicRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindPanicAlertRepository(
        panicAlertRepositoryImpl: PanicAlertRepositoryImpl
    ): PanicAlertRepository

    @Binds
    @Singleton
    abstract fun bindUserActivityRepository(
        userActivityRepositoryImpl: UserActivityRepositoryImpl
    ): UserActivityRepository

    @Binds
    abstract fun bindCallRepository(
        callRepositoryImpl: CallRepositoryImpl
    ): CallRepository
}

