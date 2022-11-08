package com.strawhat.looker.search.domain.repository

import com.strawhat.looker.map.domain.model.Place
import com.strawhat.looker.search.domain.category.SearchCategory

interface SearchRepository {

    suspend fun search(
        lat: Double,
        long: Double,
        categoryId: String?,
        value: String?,
        isAvailable: Boolean?,
    ): List<Place>

    suspend fun getCategories(): List<SearchCategory>

}