package com.williamfq.data.di

import android.content.Context
import androidx.room.Room
import com.williamfq.data.AppDatabase
import com.williamfq.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "xhat_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStoryDao(database: AppDatabase): StoryDao = database.storyDao()

    @Provides
    @Singleton
    fun provideChatMessageDao(database: AppDatabase): ChatMessageDao = database.chatMessageDao()

    @Provides
    @Singleton
    fun provideCommunityDao(database: AppDatabase): CommunityDao = database.communityDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideChannelDao(database: AppDatabase): ChannelDao = database.channelDao()

    @Provides
    @Singleton
    fun provideReactionDao(database: AppDatabase): ReactionDao = database.reactionDao()

    @Provides
    @Singleton
    fun provideCallHistoryDao(database: AppDatabase): CallHistoryDao = database.callHistoryDao()

    @Provides
    @Singleton
    fun provideSettingsDao(database: AppDatabase): SettingsDao = database.settingsDao()

    @Provides
    @Singleton
    fun provideMediaDao(database: AppDatabase): MediaDao = database.mediaDao()

    @Provides
    @Singleton
    fun provideNotificationDao(database: AppDatabase): NotificationDao = database.notificationDao()

    @Provides
    @Singleton
    fun provideChatDao(database: AppDatabase): ChatDao = database.chatDao()

    // **Nuevo método para PanicDao**
    @Provides
    @Singleton
    fun providePanicDao(database: AppDatabase): PanicDao = database.panicDao()
}
