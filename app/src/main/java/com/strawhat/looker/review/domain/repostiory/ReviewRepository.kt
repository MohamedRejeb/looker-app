package com.strawhat.looker.review.domain.repostiory

import com.strawhat.looker.review.domain.model.Review
import com.strawhat.looker.review.domain.model.ReviewsData

interface ReviewRepository {

    suspend fun getReviewsData(placeId: String): ReviewsData

    suspend fun addReview(content: String, rate: Int, placeId: String): Review

    suspend fun updateReview(content: String, rate: Int, placeId: String): Review

    suspend fun deleteReview(placeId: String)

}