
package com.williamfq.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.williamfq.domain.model.MessageStatus
import com.williamfq.domain.model.MessageType

/**
 * Entidad para almacenar mensajes en la base de datos local
 * Created by William8677 on 2025-01-04 02:56:19
 */
@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chatId: String,
    val senderId: String,
    val recipientId: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean,
    val isSent: Boolean,
    val isDeleted: Boolean,
    val type: MessageType,
    val status: MessageStatus
)