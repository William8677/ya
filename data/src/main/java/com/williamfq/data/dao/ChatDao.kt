package com.williamfq.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.williamfq.data.entities.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert
    suspend fun insertMessage(chatMessage: ChatMessageEntity)

    @Update
    suspend fun updateMessageStatus(chatMessage: ChatMessageEntity)

    @Query("SELECT * FROM chat_messages WHERE chat_id = :chatId ORDER BY timestamp DESC")
    fun getMessagesByChatId(chatId: String): Flow<List<ChatMessageEntity>>

    @Query("SELECT * FROM chat_messages WHERE is_read = 0 ORDER BY timestamp DESC")
    fun getOfflineMessages(): Flow<List<ChatMessageEntity>>

    @Query("DELETE FROM chat_messages WHERE id = :messageId")
    suspend fun deleteMessageById(messageId: Long)

    @Query("UPDATE chat_messages SET message_status = :status WHERE id = :messageId")
    suspend fun updateMessageStatusById(messageId: Long, status: String)
}
