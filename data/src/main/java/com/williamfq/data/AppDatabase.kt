package com.williamfq.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.williamfq.data.converters.DateConverter
import com.williamfq.data.converters.ListConverter
import com.williamfq.data.converters.PanicAlertConverters
import com.williamfq.data.dao.*
import com.williamfq.data.entities.*

/**
 * Base de datos principal para Xhat.
 */
@Database(
    entities = [
        ChatMessageEntity::class,
        Story::class,
        CommunityEntity::class,
        Notification::class,
        UserEntity::class,
        Settings::class,
        Reaction::class,
        Channel::class,
        CallHistory::class,
        Media::class,
        PanicAlertEntity::class,
        LocationEntity::class,
        Session::class // Agregado para soportar SessionDao
    ],
    version = 3, // Versión de la base de datos
    exportSchema = true
)
@TypeConverters(
    DateConverter::class,
    ListConverter::class,
    PanicAlertConverters::class // Conversores necesarios para las entidades
)
abstract class AppDatabase : RoomDatabase() {

    // Declaración de DAOs
    abstract fun chatMessageDao(): ChatMessageDao
    abstract fun storyDao(): StoryDao
    abstract fun userDao(): UserDao
    abstract fun communityDao(): CommunityDao
    abstract fun notificationDao(): NotificationDao
    abstract fun settingsDao(): SettingsDao
    abstract fun reactionDao(): ReactionDao
    abstract fun channelDao(): ChannelDao
    abstract fun callHistoryDao(): CallHistoryDao
    abstract fun mediaDao(): MediaDao
    abstract fun panicDao(): PanicDao
    abstract fun sessionDao(): SessionDao // Agregado para SessionDao
    abstract fun chatDao(): ChatDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS panic_alerts (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        userId TEXT NOT NULL,
                        message TEXT NOT NULL,
                        timestamp INTEGER NOT NULL
                    )
                    """
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS location (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        userId TEXT NOT NULL,
                        latitude REAL NOT NULL,
                        longitude REAL NOT NULL,
                        timestamp INTEGER NOT NULL
                    )
                    """
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS call_history (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        userId TEXT NOT NULL,
                        callType TEXT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        duration INTEGER NOT NULL,
                        isMissed INTEGER NOT NULL DEFAULT 0
                    )
                    """
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS media (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        messageId TEXT NOT NULL,
                        mediaType TEXT NOT NULL,
                        filePath TEXT NOT NULL,
                        timestamp INTEGER NOT NULL
                    )
                    """
                )
            }
        }

        /**
         * Obtiene una instancia singleton de la base de datos.
         */
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "xhat_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .fallbackToDestructiveMigrationFrom(1)
                    .build()

                INSTANCE = instance
                instance
            }
        }

        /**
         * Crea una base de datos en memoria para pruebas.
         * WARNING: No usar en producción.
         */
        fun createInMemoryDatabase(context: Context): AppDatabase {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                AppDatabase::class.java
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
        }
    }
}

