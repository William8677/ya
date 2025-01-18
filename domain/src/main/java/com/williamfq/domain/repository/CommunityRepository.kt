package com.williamfq.domain.repository

import com.williamfq.domain.model.Community
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun getCommunities(): Flow<List<Community>>
    suspend fun createCommunity(community: Community): Result<Unit>
    suspend fun subscribeCommunity(communityId: String): Result<Unit>
    suspend fun searchCommunities(query: String): Flow<List<Community>>
}
