package com.strawhat.looker.review.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewApi {

    @GET("user/reviews/{placeId}")
    suspend fun getReviewsData(
        @Path("placeId") placeId: String,
    ): Response<ReviewsResponse>


    @POST("user/reviews/{placeId}")
    suspend fun addReview(
        @Path("placeId") placeId: String,
        @Body addReviewRequest: AddReviewRequest,
    ): Response<AddReviewResponse>

    @PUT("user/reviews/{placeId}")
    suspend fun updateReview(
        @Path("placeId") placeId: String,
        @Body updateReviewRequest: AddReviewRequest,
    ): Response<AddReviewResponse>

    @DELETE("user/reviews/{placeId}")
    suspend fun deleteReview(
        @Path("placeId") placeId: String,
    ): Response<ResponseBody?>

}