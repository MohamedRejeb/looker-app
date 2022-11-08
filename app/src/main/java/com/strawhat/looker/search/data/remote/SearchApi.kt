package com.strawhat.looker.search.data.remote

import com.strawhat.looker.map.data.remote.PlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchApi {

    @GET("user/places")
    suspend fun search(
        @QueryMap queries: HashMap<String, String>,
    ): Response<PlacesResponse>

    @GET("user/categories")
    suspend fun getCategories(): Response<SearchCategoriesResponse>

}