package com.williamfq.data.converters

import androidx.room.TypeConverter
import com.williamfq.data.entities.MessageTypeEntity
import com.williamfq.data.entities.MessageStatusEntity

class ChatMessageConverters {

    /**
     * Convierte MessageTypeEntity a String para almacenar en la base de datos.
     */
    @TypeConverter
    fun fromMessageType(value: MessageTypeEntity): String = value.name

    /**
     * Convierte String de la base de datos a MessageTypeEntity.
     * Usa TEXT como valor predeterminado si la conversión falla.
     */
    @TypeConverter
    fun toMessageType(value: String): MessageTypeEntity =
        runCatching { MessageTypeEntity.valueOf(value) }.getOrDefault(MessageTypeEntity.TEXT)

    /**
     * Convierte MessageStatusEntity a String para almacenar en la base de datos.
     */
    @TypeConverter
    fun fromMessageStatus(value: MessageStatusEntity): String = value.name

    /**
     * Convierte String de la base de datos a MessageStatusEntity.
     * Usa SENDING como valor predeterminado si la conversión falla.
     */
    @TypeConverter
    fun toMessageStatus(value: String): MessageStatusEntity =
        runCatching { MessageStatusEntity.valueOf(value) }.getOrDefault(MessageStatusEntity.SENDING)
}
