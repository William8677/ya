package com.williamfq.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.williamfq.data.converters.ChatMessageConverters

/**
 * Representa un mensaje de chat en la base de datos.
 */
@Entity(tableName = "chat_messages")
@TypeConverters(ChatMessageConverters::class) // Indica a Room que use convertidores personalizados
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "chat_id")
    val chatId: String,

    @ColumnInfo(name = "sender_id")
    val senderId: String,

    @ColumnInfo(name = "recipient_id")
    val recipientId: String,

    @ColumnInfo(name = "message_content")
    val messageContent: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "is_read")
    val isRead: Boolean = false,

    @ColumnInfo(name = "is_sent")
    val isSent: Boolean = true,

    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false,

    @ColumnInfo(name = "message_type")
    val messageType: MessageTypeEntity = MessageTypeEntity.TEXT,

    @ColumnInfo(name = "message_status")
    val messageStatus: MessageStatusEntity = MessageStatusEntity.SENDING
)
