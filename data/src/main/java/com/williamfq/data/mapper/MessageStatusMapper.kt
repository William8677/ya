// data/src/main/java/com/williamfq/data/mapper/MessageStatusMapper.kt
package com.williamfq.data.mapper

import com.williamfq.data.entities.MessageStatusEntity
import com.williamfq.domain.model.MessageStatus

fun MessageStatus.toEntity(): MessageStatusEntity = when (this) {
    MessageStatus.SENDING -> MessageStatusEntity.SENDING
    MessageStatus.SENT -> MessageStatusEntity.SENT
    MessageStatus.FAILED -> MessageStatusEntity.FAILED
    MessageStatus.DELIVERED -> MessageStatusEntity.DELIVERED
    MessageStatus.READ -> MessageStatusEntity.READ
}

fun MessageStatusEntity.toDomain(): MessageStatus = when (this) {
    MessageStatusEntity.SENDING -> MessageStatus.SENDING
    MessageStatusEntity.SENT -> MessageStatus.SENT
    MessageStatusEntity.FAILED -> MessageStatus.FAILED
    MessageStatusEntity.DELIVERED -> MessageStatus.DELIVERED
    MessageStatusEntity.READ -> MessageStatus.READ
}
