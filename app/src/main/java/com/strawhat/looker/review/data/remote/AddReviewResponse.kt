package com.strawhat.looker.review.data.remote

import com.squareup.moshi.Json

data class AddReviewResponse(
    val payload: AddReviewPayload,
) {
    data class AddReviewPayload(
        val review: ReviewResponse
    ) {
        data class ReviewResponse(
            @Json(name = "_id")
            val id: String,
            @Json(name = "place")
            val placeId: String,
            val user: ReviewUserResponse,
            val comment: String? = null,
            val amount: Float,
            val createdAt: String,
        )
    }
}
