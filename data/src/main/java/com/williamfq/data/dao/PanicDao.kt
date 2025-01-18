// data/src/main/java/com/williamfq/data/dao/PanicDao.kt
package com.williamfq.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.williamfq.data.entities.PanicAlertEntity
import com.williamfq.data.entities.PanicAlertStatusEntity

@Dao
interface PanicDao {

    /**
     * Inserta una nueva alerta de pánico en la base de datos.
     *
     * @param alert La alerta de pánico a insertar.
     * @return El ID de la alerta insertada.
     */
    @Insert
    suspend fun insertPanicAlert(alert: PanicAlertEntity): Long

    /**
     * Recupera todas las alertas de pánico enviadas por un usuario específico.
     *
     * @param userId ID del usuario.
     * @return Lista de alertas de pánico.
     */
    @Query("SELECT * FROM panic_alerts WHERE user_id = :userId ORDER BY timestamp DESC")
    suspend fun getPanicAlertsByUserId(userId: String): List<PanicAlertEntity>

    /**
     * Actualiza el estado de una alerta de pánico.
     *
     * @param alertId ID de la alerta a actualizar.
     * @param status Nuevo estado de la alerta.
     */
    @Query("UPDATE panic_alerts SET status = :status WHERE id = :alertId")
    suspend fun updatePanicAlertStatus(alertId: Long, status: PanicAlertStatusEntity)
}
