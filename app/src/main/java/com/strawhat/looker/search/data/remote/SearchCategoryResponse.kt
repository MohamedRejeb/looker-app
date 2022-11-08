package com.strawhat.looker.search.data.remote

import com.squareup.moshi.Json

data class SearchCategoriesResponse(
    val payload: SearchCategoriesPayload,
)

data class SearchCategoriesPayload(
    val categories: List<SearchCategoryResponse>,
)

data class SearchCategoryResponse(
    @Json(name = "_id")
    val id: String,
    val name: String,
)
