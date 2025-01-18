package com.williamfq.domain

import com.williamfq.domain.model.ChatMessage

interface ChatRepository {
    suspend fun sendMessage(message: ChatMessage)
    suspend fun getMessagesByChat(chatId: String): List<ChatMessage>
}
