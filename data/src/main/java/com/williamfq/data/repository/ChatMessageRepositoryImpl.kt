// data/src/main/java/com/williamfq/data/repository/ChatMessageRepositoryImpl.kt
package com.williamfq.data.repository

import com.williamfq.data.dao.ChatMessageDao
import com.williamfq.data.mapper.toDomain
import com.williamfq.data.mapper.toEntity
import com.williamfq.domain.model.ChatMessage
import com.williamfq.domain.repository.ChatMessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementación de la interfaz 'ChatMessageRepository' de la capa dominio,
 * usando la base de datos local (DAO) para persistir los mensajes.
 */
class ChatMessageRepositoryImpl @Inject constructor(
    private val chatMessageDao: ChatMessageDao
) : ChatMessageRepository {

    override suspend fun sendMessage(message: ChatMessage) {
        withContext(Dispatchers.IO) {
            val entity = message.toEntity()
            chatMessageDao.insertMessage(entity)
        }
    }

    override suspend fun getMessages(chatId: String): List<ChatMessage> {
        return withContext(Dispatchers.IO) {
            chatMessageDao.getMessagesByChatId(chatId)
                .map { it.toDomain() }
        }
    }
}
