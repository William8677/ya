// data/src/main/java/com/williamfq/data/mapper/MessageTypeMapper.kt
package com.williamfq.data.mapper

import com.williamfq.data.entities.MessageTypeEntity
import com.williamfq.domain.model.MessageType

fun MessageType.toEntity(): MessageTypeEntity = when (this) {
    MessageType.TEXT -> MessageTypeEntity.TEXT
    MessageType.IMAGE -> MessageTypeEntity.IMAGE
    MessageType.VIDEO -> MessageTypeEntity.VIDEO
    MessageType.AUDIO -> MessageTypeEntity.AUDIO
    MessageType.FILE -> MessageTypeEntity.FILE
}

fun MessageTypeEntity.toDomain(): MessageType = when (this) {
    MessageTypeEntity.TEXT -> MessageType.TEXT
    MessageTypeEntity.IMAGE -> MessageType.IMAGE
    MessageTypeEntity.VIDEO -> MessageType.VIDEO
    MessageTypeEntity.AUDIO -> MessageType.AUDIO
    MessageTypeEntity.FILE -> MessageType.FILE
}
