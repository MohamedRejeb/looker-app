package com.strawhat.looker.review.data.repostiory

import com.strawhat.looker.review.data.remote.AddReviewRequest
import com.strawhat.looker.review.data.remote.ReviewRemoteDataSource
import com.strawhat.looker.review.domain.model.Review
import com.strawhat.looker.review.domain.model.ReviewsData
import com.strawhat.looker.review.domain.repostiory.ReviewRepository
import com.strawhat.looker.utils.error_handling.getResult
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val remote: ReviewRemoteDataSource,
): ReviewRepository {

    override suspend fun getReviewsData(placeId: String): ReviewsData {
        return remote.getReviewsData(placeId).getResult {
            ReviewsData(
                count = it.payload.data?.count ?: 0,
                average = it.payload.data?.avg ?: 0f,
                reviews = it.payload.data?.reviews?.map { reviewResponse ->
                    Review(
                        id = reviewResponse.id,
                        placeId = reviewResponse.placeId,
                        userName = reviewResponse.user.first().firstName + " " + reviewResponse.user.first().lastName,
                        userImage = reviewResponse.user.first().avatar ?: "",
                        content = reviewResponse.comment ?: "",
                        rate = reviewResponse.amount,
                        createdAt = reviewResponse.createdAt,
                        isCurrentUser = reviewResponse.isUser,
                    )
                } ?: emptyList()
            )
        }
    }

    override suspend fun addReview(content: String, rate: Int, placeId: String): Review {
        return remote.addReview(
            placeId = placeId,
            addReviewRequest = AddReviewRequest(
                comment = content,
                amount = rate,
            )
        ).getResult {
            val reviewResponse = it.payload.review
            Review(
                id = reviewResponse.id,
                placeId = reviewResponse.placeId,
                userName = reviewResponse.user.firstName + " " + reviewResponse.user.lastName,
                userImage = reviewResponse.user.avatar ?: "",
                content = reviewResponse.comment ?: "",
                rate = reviewResponse.amount,
                createdAt = reviewResponse.createdAt,
                isCurrentUser = true,
            )
        }
    }

    override suspend fun updateReview(content: String, rate: Int, placeId: String): Review {
        return remote.updateReview(
            placeId = placeId,
            updateReviewRequest = AddReviewRequest(
                comment = content,
                amount = rate
            )
        ).getResult {
            val reviewResponse = it.payload.review
            Review(
                id = reviewResponse.id,
                placeId = reviewResponse.placeId,
                userName = reviewResponse.user.firstName + " " + reviewResponse.user.lastName,
                userImage = reviewResponse.user.avatar ?: "",
                content = reviewResponse.comment ?: "",
                rate = reviewResponse.amount,
                createdAt = reviewResponse.createdAt,
                isCurrentUser = true,
            )
        }
    }

    override suspend fun deleteReview(placeId: String) {
        remote.deleteReview(
            placeId = placeId
        ).getResult { it }
    }
}