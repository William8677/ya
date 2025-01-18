package com.williamfq.data.dao

import androidx.room.*
import com.williamfq.data.entities.Story

/**
 * DAO para gestionar las historias en Xhat.
 */
@Dao
interface StoryDao {

    // Obtener una historia por su ID
    @Query("SELECT * FROM stories WHERE id = :id")
    suspend fun getStoryById(id: Int): Story?

    // Insertar o reemplazar una historia
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: Story)

    // Insertar o reemplazar múltiples historias
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<Story>)

    // Actualizar una historia existente
    @Update
    suspend fun updateStory(story: Story)

    // Eliminar una historia por su ID
    @Query("DELETE FROM stories WHERE id = :id")
    suspend fun deleteStoryById(id: Int)

    // Eliminar todas las historias
    @Query("DELETE FROM stories")
    suspend fun deleteAllStories()

    // Obtener todas las historias ordenadas por tiempo
    @Query("SELECT * FROM stories ORDER BY timestamp DESC")
    suspend fun getAllStories(): List<Story>

    // Obtener historias activas
    @Query("SELECT * FROM stories WHERE isActive = 1 ORDER BY timestamp DESC")
    suspend fun getActiveStories(): List<Story>

    // Buscar historias por título o descripción
    @Query("SELECT * FROM stories WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    suspend fun searchStories(query: String): List<Story>

    // Incrementar el número de vistas de una historia
    @Query("UPDATE stories SET views = views + 1 WHERE id = :id")
    suspend fun incrementStoryViews(id: Int)

    // Obtener historias publicadas por un usuario específico
    @Query("SELECT * FROM stories WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getStoriesByUser(userId: String): List<Story>

    // Filtrar historias por etiquetas
    @Query("SELECT * FROM stories WHERE ',' || tags || ',' LIKE '%,' || :tag || ',%' ORDER BY timestamp DESC")
    suspend fun getStoriesByTag(tag: String): List<Story>

    // Obtener estadísticas generales de las historias
    @Query("SELECT SUM(views) AS totalViews, SUM(Reactions) AS totalReactions FROM stories")
    suspend fun getStoryStats(): StoryStats
}

/**
 * Clase auxiliar para estadísticas de historias.
 */
data class StoryStats(
    val totalViews: Int = 0,
    val totalReactions: Int = 0
)
