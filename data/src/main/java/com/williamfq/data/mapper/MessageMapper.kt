// data/src/main/java/com/williamfq/data/mapper/MessageMapper.kt
package com.williamfq.data.mapper

import com.williamfq.data.entities.MessageEntity
import com.williamfq.domain.model.ChatMessage
import javax.inject.Inject

/**
 * Mapper para convertir entre MessageEntity y ChatMessage
 * Created by William8677 on 2025-01-04 02:56:19
 */
class MessageMapper @Inject constructor() {
    fun mapToDomain(entity: MessageEntity): ChatMessage {
        return ChatMessage(
            id = entity.id,
            chatId = entity.chatId,
            senderId = entity.senderId,
            recipientId = entity.recipientId,
            message = entity.message,
            timestamp = entity.timestamp,
            isRead = entity.isRead,
            isSent = entity.isSent,
            isDeleted = entity.isDeleted,
            type = entity.type,
            status = entity.status
        )
    }

    fun mapToEntity(domain: ChatMessage): MessageEntity {
        return MessageEntity(
            id = domain.id,
            chatId = domain.chatId,
            senderId = domain.senderId,
            recipientId = domain.recipientId,
            message = domain.message,
            timestamp = domain.timestamp,
            isRead = domain.isRead,
            isSent = domain.isSent,
            isDeleted = domain.isDeleted,
            type = domain.type,
            status = domain.status
        )
    }
}