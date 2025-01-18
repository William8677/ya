package com.williamfq.data.dao

import androidx.room.*
import com.williamfq.data.entities.ChatMessageEntity

@Dao
interface ChatMessageDao {

    /**
     * Inserta un mensaje en la base de datos.
     * Reemplaza cualquier mensaje anterior con el mismo primary key.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity)

    /**
     * Actualiza un mensaje existente en la base de datos.
     */
    @Update
    suspend fun updateMessage(message: ChatMessageEntity)

    /**
     * Elimina un mensaje de la base de datos.
     */
    @Delete
    suspend fun deleteMessage(message: ChatMessageEntity)

    /**
     * Recupera todos los mensajes de un chat específico,
     * ordenados por 'timestamp' de manera descendente (el más reciente primero).
     *
     * @param chatId ID único del chat.
     * @return Lista de mensajes asociados al 'chatId' indicado.
     */
    @Query("SELECT * FROM chat_messages WHERE chat_id = :chatId ORDER BY timestamp DESC")
    suspend fun getMessagesByChatId(chatId: String): List<ChatMessageEntity>
}
