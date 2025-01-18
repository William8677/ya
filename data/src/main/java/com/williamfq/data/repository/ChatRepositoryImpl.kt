package com.williamfq.data.repository

import com.williamfq.domain.model.ChatMessage
import com.williamfq.domain.model.MessageStatus
import com.williamfq.domain.repository.ChatRepository
import com.williamfq.data.dao.ChatDao
import com.williamfq.data.mapper.toDomain
import com.williamfq.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao
) : ChatRepository {

    override fun getOfflineMessages(): Flow<List<ChatMessage>> =
        chatDao.getOfflineMessages().map { entities -> entities.map { it.toDomain() } }

    override fun getMessagesByChatId(chatId: String): Flow<List<ChatMessage>> =
        chatDao.getMessagesByChatId(chatId).map { entities -> entities.map { it.toDomain() } }

    override suspend fun saveMessage(message: ChatMessage) =
        withContext(Dispatchers.IO) {
            chatDao.insertMessage(message.toEntity())
        }

    override suspend fun deleteMessage(messageId: Long) =
        withContext(Dispatchers.IO) {
            chatDao.deleteMessageById(messageId)
        }

    override suspend fun updateMessageStatus(messageId: Long, status: MessageStatus) =
        withContext(Dispatchers.IO) {
            chatDao.updateMessageStatusById(messageId, status.name)
        }

    override suspend fun sendMessage(chatMessage: ChatMessage) =
        withContext(Dispatchers.IO) {
            chatDao.insertMessage(chatMessage.toEntity())
        }
}
