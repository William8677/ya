// data/src/main/java/com/williamfq/data/entities/PanicAlertEntity.kt
package com.williamfq.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.williamfq.data.converters.PanicAlertConverters

/**
 * Representa una alerta de pánico en la base de datos.
 */
@Entity(tableName = "panic_alerts")
data class PanicAlertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "message")
    val message: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "location")
    val location: LocationEntity? = null,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "status")
    val status: PanicAlertStatusEntity = PanicAlertStatusEntity.SENDING
)
