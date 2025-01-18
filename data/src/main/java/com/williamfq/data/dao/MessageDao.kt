// data/src/main/java/com/williamfq/data/local/dao/MessageDao.kt
package com.williamfq.data.dao

import androidx.room.*
import com.williamfq.data.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones de mensajes en la base de datos
 * Created by William8677 on 2025-01-04 02:56:19
 */
@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp DESC")
    fun getMessagesByChatId(chatId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Query("UPDATE messages SET status = :status WHERE id = :messageId")
    suspend fun updateMessageStatus(messageId: Long, status: String)
}