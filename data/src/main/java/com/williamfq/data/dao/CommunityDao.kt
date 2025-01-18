package com.williamfq.data.dao

import androidx.room.*
import com.williamfq.data.entities.CommunityEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para las operaciones de base de datos relacionadas con comunidades.
 * Creado por William8677 el 2025-01-04
 */
@Dao
interface CommunityDao {
    /**
     * Inserta una nueva comunidad en la base de datos
     * @param communityEntity La comunidad a insertar
     * @return El ID de la comunidad insertada
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunity(communityEntity: CommunityEntity): Long

    /**
     * Inserta múltiples comunidades en la base de datos
     * @param communities Lista de comunidades a insertar
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunities(communities: List<CommunityEntity>)

    /**
     * Actualiza una comunidad existente
     * @param communityEntity La comunidad a actualizar
     */
    @Update
    suspend fun updateCommunity(communityEntity: CommunityEntity)

    /**
     * Actualiza el estado de suscripción de una comunidad
     * @param communityId ID de la comunidad
     * @param status Nuevo estado de suscripción
     */
    @Query("UPDATE communities SET isActive = :status WHERE id = :communityId")
    suspend fun updateSubscriptionStatus(communityId: Long, status: Boolean)

    /**
     * Elimina una comunidad por su ID
     * @param communityId ID de la comunidad a eliminar
     */
    @Query("DELETE FROM communities WHERE id = :communityId")
    suspend fun deleteCommunityById(communityId: Long)

    /**
     * Obtiene una comunidad por su ID
     * @param communityId ID de la comunidad
     * @return La comunidad encontrada o null si no existe
     */
    @Query("SELECT * FROM communities WHERE id = :communityId")
    suspend fun getCommunityById(communityId: Long): CommunityEntity?

    /**
     * Obtiene todas las comunidades ordenadas por fecha de creación
     * @return Lista de comunidades
     */
    @Query("SELECT * FROM communities ORDER BY createdAt DESC")
    suspend fun getAllCommunities(): List<CommunityEntity>

    /**
     * Obtiene las comunidades activas del usuario
     * @param userId ID del usuario
     * @return Flow de lista de comunidades
     */
    @Query("""
        SELECT * FROM communities 
        WHERE isActive = 1 
        AND (createdBy = :userId OR isSubscribed = 1)
        ORDER BY lastActivityAt DESC
    """)
    fun getUserCommunities(userId: String): Flow<List<CommunityEntity>>

    /**
     * Busca comunidades por nombre o descripción
     * @param query Término de búsqueda
     * @return Lista de comunidades que coinciden
     */
    @Query("""
        SELECT * FROM communities 
        WHERE isActive = 1 
        AND (name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY memberCount DESC
    """)
    suspend fun searchCommunities(query: String): List<CommunityEntity>

    /**
     * Obtiene las comunidades por categoría
     * @param category Categoría a buscar
     * @return Lista de comunidades de esa categoría
     */
    @Query("""
        SELECT * FROM communities 
        WHERE isActive = 1 
        AND categories LIKE '%' || :category || '%'
        ORDER BY memberCount DESC
    """)
    suspend fun getCommunityByCategory(category: String): List<CommunityEntity>

    /**
     * Actualiza el contador de miembros de una comunidad
     * @param communityId ID de la comunidad
     * @param increment Cantidad a incrementar (positiva o negativa)
     */
    @Query("UPDATE communities SET memberCount = memberCount + :increment WHERE id = :communityId")
    suspend fun updateMemberCount(communityId: Long, increment: Int)
}
