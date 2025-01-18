// XhatDatabase.kt
package com.williamfq.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.williamfq.data.converters.Converters
import com.williamfq.data.dao.CommunityDao
import com.williamfq.data.dao.MessageDao
import com.williamfq.data.dao.UserDao
import com.williamfq.data.entities.CommunityEntity
import com.williamfq.data.entities.MessageEntity
import com.williamfq.data.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        MessageEntity::class,
        CommunityEntity::class  // Agregamos la entidad de Community
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class XhatDatabase : RoomDatabase() {

    // DAOs
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun communityDao(): CommunityDao  // Agregamos el DAO de Community

    companion object {
        const val DATABASE_NAME = "xhat_database"
    }
}
