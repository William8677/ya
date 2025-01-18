package com.williamfq.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.williamfq.data.converters.StoryConverters

/**
 * Entidad que representa una historia en Xhat.
 */
@Entity(tableName = "stories")
@TypeConverters(StoryConverters::class) // Necesario para manejar tipos personalizados
data class Story(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID único autogenerado
    val userId: String, // ID del usuario que publicó la historia
    val title: String, // Título de la historia
    val description: String, // Descripción de la historia
    val mediaUrl: String? = null, // URL del contenido multimedia (opcional)
    val mediaType: MediaType = MediaType.TEXT, // Tipo de contenido multimedia (por defecto TEXTO)
    val timestamp: Long = System.currentTimeMillis(), // Marca de tiempo predeterminada
    val isActive: Boolean = true, // Estado de la historia (activa/inactiva)
    val views: Int = 0, // Número inicial de vistas
    val duration: Int = 24, // Duración de la historia en horas (por defecto 24 horas)
    val tags: List<String> = emptyList(), // Etiquetas asociadas (por defecto lista vacía)
    val comments: List<Comment> = emptyList(), // Comentarios en la historia
    val reactions: List<Reaction> = emptyList(), // Reacciones en la historia
    val poll: Poll? = null // Encuesta asociada (opcional)
) {
    companion object {
        fun importFromTikTok(tiktokData: String): Story {
            // Lógica para convertir los datos de TikTok a una instancia de Story
            return Story(
                userId = "tiktokUserId",
                title = "TikTok Story",
                description = tiktokData,
                mediaType = MediaType.VIDEO
            )
        }

        fun exportToTikTok(story: Story): String {
            // Lógica para convertir la instancia de Story a un formato compatible con TikTok
            return "Exported to TikTok: ${story.title}"
        }

        fun importFromTwitter(twitterData: String): Story {
            // Lógica para convertir los datos de Twitter a una instancia de Story
            return Story(
                userId = "twitterUserId",
                title = "Twitter Story",
                description = twitterData,
                mediaType = MediaType.TEXT
            )
        }

        fun exportToTwitter(story: Story): String {
            // Lógica para convertir la instancia de Story a un formato compatible con Twitter
            return "Exported to Twitter: ${story.title}"
        }
    }
}

/**
 * Enumeración que define los tipos de contenido multimedia permitidos.
 */
enum class MediaType {
    IMAGE,
    VIDEO,
    TEXT
}

/**
 * Clase que representa un comentario en una historia.
 */
data class Comment(
    val userId: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Enumeración que define los tipos de reacciones permitidos.
 */
enum class ReactionType {
    LIKE,
    LOVE,
    HAHA,
    WOW,
    SAD,
    ANGRY
}
