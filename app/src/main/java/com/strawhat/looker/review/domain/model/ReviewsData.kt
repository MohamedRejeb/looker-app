package com.strawhat.looker.review.domain.model

data class ReviewsData(
    val reviews: List<Review>,
    val count: Int,
    val average: Float,
)