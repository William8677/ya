package com.williamfq.data.mapper

import com.williamfq.domain.model.ChatMessage
import com.williamfq.domain.model.MessageStatus
import com.williamfq.domain.model.MessageType
import com.williamfq.data.entities.ChatMessageEntity
import com.williamfq.data.entities.MessageStatusEntity
import com.williamfq.data.entities.MessageTypeEntity

fun ChatMessage.toEntity(): ChatMessageEntity =
    ChatMessageEntity(
        id = this.id,
        chatId = this.chatId,
        senderId = this.senderId,
        recipientId = this.recipientId,
        messageContent = this.message, // Mapeo a messageContent en la entidad
        timestamp = this.timestamp,
        isRead = this.isRead,
        isSent = this.isSent,
        isDeleted = this.isDeleted,
        messageType = MessageTypeEntity.valueOf(this.type.name), // Mapeo de tipo
        messageStatus = MessageStatusEntity.valueOf(this.status.name) // Mapeo de estado
    )

fun ChatMessageEntity.toDomain(): ChatMessage =
    ChatMessage(
        id = this.id,
        chatId = this.chatId,
        senderId = this.senderId,
        recipientId = this.recipientId,
        message = this.messageContent, // Mapeo inverso de messageContent
        timestamp = this.timestamp,
        isRead = this.isRead,
        isSent = this.isSent,
        isDeleted = this.isDeleted,
        type = MessageType.valueOf(this.messageType.name), // Mapeo inverso de tipo
        status = MessageStatus.valueOf(this.messageStatus.name) // Mapeo inverso de estado
    )
