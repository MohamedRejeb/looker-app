package com.strawhat.looker.review.data.remote

data class AddReviewRequest(
    val comment: String,
    val amount: Int,
)