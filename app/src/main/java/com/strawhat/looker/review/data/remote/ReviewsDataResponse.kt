package com.strawhat.looker.review.data.remote

import com.squareup.moshi.Json

data class ReviewsResponse(
    val payload: ReviewsDataPayload
)

data class ReviewsDataPayload(
    val data: ReviewsDataResponse? = null
)

data class ReviewsDataResponse(
    @Json(name = "_id")
    val placeId: String,
    val count: Int,
    val avg: Float,
    val reviews: List<ReviewResponse>,

)

data class ReviewResponse(
    @Json(name = "_id")
    val id: String,
    @Json(name = "place")
    val placeId: String,
    val user: List<ReviewUserResponse>,
    val comment: String? = null,
    val amount: Float,
    val isUser: Boolean,
    val createdAt: String,
)

data class ReviewUserResponse(
    @Json(name = "_id")
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatar: String? = null,
)