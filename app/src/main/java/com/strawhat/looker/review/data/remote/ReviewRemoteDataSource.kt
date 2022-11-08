package com.strawhat.looker.review.data.remote

import javax.inject.Inject

class ReviewRemoteDataSource @Inject constructor(
    private val reviewApi: ReviewApi,
) {

    suspend fun getReviewsData(placeId: String) =
        reviewApi.getReviewsData(placeId)

    suspend fun addReview(placeId: String, addReviewRequest: AddReviewRequest) =
        reviewApi.addReview(placeId, addReviewRequest)

    suspend fun updateReview(placeId: String, updateReviewRequest: AddReviewRequest) =
        reviewApi.updateReview(placeId, updateReviewRequest)

    suspend fun deleteReview(placeId: String) =
        reviewApi.deleteReview(placeId)

}