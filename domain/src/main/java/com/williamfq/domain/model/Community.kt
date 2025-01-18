package com.williamfq.domain.model

/**
 * Modelo de dominio que representa una comunidad.
 * Esta clase se utiliza en la capa de dominio de la aplicación.
 */
data class Community(
    val id: Long = 0,

    val name: String,

    val description: String? = null,

    val createdBy: String,

    val createdAt: Long = System.currentTimeMillis(),

    val isPrivate: Boolean = false,

    val isActive: Boolean = true,

    val memberCount: Int = 0,

    val categories: List<String> = emptyList(),

    val avatarUrl: String? = null,

    val rules: List<String> = emptyList(),

    val isSubscribed: Boolean = false,

    val lastActivityAt: Long = System.currentTimeMillis(),

    val pinnedMessages: List<Long> = emptyList(),

    val moderators: List<String> = emptyList(),

    val visibility: Visibility = Visibility.PUBLIC
) {
    /**
     * Comprueba si un usuario dado es moderador de la comunidad
     * @param userId ID del usuario a verificar
     * @return true si el usuario es moderador, false en caso contrario
     */
    fun isModerator(userId: String): Boolean = userId in moderators

    /**
     * Comprueba si un usuario dado es el creador de la comunidad
     * @param userId ID del usuario a verificar
     * @return true si el usuario es el creador, false en caso contrario
     */
    fun isCreator(userId: String): Boolean = userId == createdBy

    /**
     * Comprueba si un usuario tiene permisos de administración
     * @param userId ID del usuario a verificar
     * @return true si el usuario es creador o moderador, false en caso contrario
     */
    fun hasAdminPermissions(userId: String): Boolean = isCreator(userId) || isModerator(userId)

    companion object {
        const val MAX_NAME_LENGTH = 50
        const val MAX_DESCRIPTION_LENGTH = 1000
        const val MAX_CATEGORIES = 5
        const val MAX_RULES = 20
        const val MAX_MODERATORS = 10
    }
}

/**
 * Enumeración que representa los diferentes niveles de visibilidad de una comunidad
 */
enum class Visibility {
    /**
     * Comunidad visible y accesible para todos los usuarios
     */
    PUBLIC,

    /**
     * Comunidad visible pero con acceso restringido
     */
    RESTRICTED,

    /**
     * Comunidad solo visible y accesible para miembros invitados
     */
    PRIVATE
}

/**
 * Sealed class que representa los diferentes estados de una comunidad
 */


/**
 * Extensiones útiles para el modelo Community
 */
fun Community.isActive(): Boolean = isActive && !isPrivate || isSubscribed

fun Community.formatLastActivity(): String {
    val now = System.currentTimeMillis()
    val diff = now - lastActivityAt
    return when {
        diff < 60000 -> "Hace un momento"
        diff < 3600000 -> "${diff / 60000} minutos"
        diff < 86400000 -> "${diff / 3600000} horas"
        else -> "${diff / 86400000} días"
    }
}
