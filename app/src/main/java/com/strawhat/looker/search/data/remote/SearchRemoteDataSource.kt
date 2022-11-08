package com.strawhat.looker.search.data.remote

import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val searchApi: SearchApi
) {

    suspend fun search(
        queries: HashMap<String, String>
    ) = searchApi.search(queries)

    suspend fun getCategories() = searchApi.getCategories()

}