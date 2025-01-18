// Archivo: com/williamfq/xhat/domain/model/Comment.kt
package com.williamfq.domain.model

data class Comment(
    val id: String,
    val postId: String,
    val author: User,
    val content: String,
    val upvotes: Int,
    val downvotes: Int,
    val timestamp: Long
)
