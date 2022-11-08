package com.strawhat.looker.review.domain.model

data class Review(
    val id: String,
    val placeId: String,
    val userName: String,
    val userImage: String,
    val content: String,
    val rate: Float,
    val isCurrentUser: Boolean,
    val createdAt: String,
)
