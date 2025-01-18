package com.williamfq.data.repository

import com.williamfq.data.dao.CommunityDao
import com.williamfq.data.mapper.toDomain
import com.williamfq.data.mapper.toEntity
import com.williamfq.domain.model.Community
import com.williamfq.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación del repositorio de comunidades.
 * Actualizado por William8677 el 2025-01-05 04:06:04
 */
@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val communityDao: CommunityDao
) : CommunityRepository {

    /**
     * Busca comunidades según el query proporcionado
     */
    override suspend fun searchCommunities(query: String): Flow<List<Community>> = flow {
        val communities = communityDao.searchCommunities(query)
        emit(communities.map { it.toDomain() })
    }

    /**
     * Obtiene todas las comunidades como un Flow
     */
    override suspend fun getCommunities(): Flow<List<Community>> =
        communityDao.getUserCommunities(CURRENT_USER)
            .map { entities ->
                entities.map { it.toDomain() }
            }

    /**
     * Crea una nueva comunidad
     */
    override suspend fun createCommunity(community: Community): Result<Unit> {
        return try {
            val communityToCreate = community.copy(
                createdAt = Instant.now().toEpochMilli(),
                createdBy = CURRENT_USER,
                isActive = true,
                lastActivityAt = Instant.now().toEpochMilli(),
                moderators = listOf(CURRENT_USER) + (community.moderators)
            )

            validate(communityToCreate)
            val id = communityDao.insertCommunity(communityToCreate.toEntity())
            if (id > 0) Result.success(Unit)
            else Result.failure(Exception("Error al crear la comunidad"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Suscribe a un usuario a una comunidad
     */
    override suspend fun subscribeCommunity(communityId: String): Result<Unit> {
        return try {
            val id = communityId.toLongOrNull()
                ?: return Result.failure(Exception("ID de comunidad inválido"))

            val community = communityDao.getCommunityById(id)
                ?: return Result.failure(Exception("Comunidad no encontrada"))

            val isCurrentlySubscribed = community.isSubscribed
            communityDao.updateSubscriptionStatus(id, !isCurrentlySubscribed)

            val increment = if (isCurrentlySubscribed) -1 else 1
            communityDao.updateMemberCount(id, increment)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        private const val CURRENT_USER = "William8677"
        private const val MAX_NAME_LENGTH = 50
        private const val MAX_DESCRIPTION_LENGTH = 1000
    }

    private fun validate(community: Community) {
        require(community.name.isNotBlank()) {
            "El nombre de la comunidad no puede estar vacío"
        }
        require(community.name.length <= MAX_NAME_LENGTH) {
            "El nombre no puede exceder $MAX_NAME_LENGTH caracteres"
        }
        community.description?.let { desc ->
            require(desc.length <= MAX_DESCRIPTION_LENGTH) {
                "La descripción no puede exceder $MAX_DESCRIPTION_LENGTH caracteres"
            }
        }
    }
}