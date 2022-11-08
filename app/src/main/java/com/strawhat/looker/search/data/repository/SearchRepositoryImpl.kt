package com.strawhat.looker.search.data.repository

import com.strawhat.looker.map.domain.model.Place
import com.strawhat.looker.search.data.remote.SearchRemoteDataSource
import com.strawhat.looker.search.domain.category.SearchCategory
import com.strawhat.looker.search.domain.repository.SearchRepository
import com.strawhat.looker.utils.error_handling.getResult
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remote: SearchRemoteDataSource
): SearchRepository {

    override suspend fun search(
        lat: Double,
        long: Double,
        categoryId: String?,
        value: String?,
        isAvailable: Boolean?
    ): List<Place> {
        val queries = HashMap<String, String>()

        queries["lat"] = lat.toString()
        queries["lan"] = long.toString()
        categoryId?.let { queries["category"] = it }
        value?.let { queries["search"] = it }
        isAvailable?.let { queries["isAvailable"] = it.toString() }

        return remote.search(queries).getResult { it.payload.places.map { placeResponse ->
            Place(
                id = placeResponse.id,
                name = placeResponse.name,
                lat = placeResponse.lat,
                long = placeResponse.lan,
                content = placeResponse.description ?: "",
                slides = placeResponse.slides.map { slideResponse ->
                    Place.Slide(
                        id = slideResponse.id,
                        image = slideResponse.url,
                    )
                },
                category = Place.Category(
                    id = placeResponse.category.id,
                    name = placeResponse.category.name,
                    image = placeResponse.category.img,
                    products = placeResponse.category.products.map { productResponse ->
                        Place.Category.Product(
                            id = productResponse.id,
                            name = productResponse.name,
                            image = productResponse.img,
                            status = when(productResponse.isAvailable) {
                                true -> Place.Category.Product.Status.Available
                                false -> Place.Category.Product.Status.Unavailable
                                null -> Place.Category.Product.Status.Unknown
                            }
                        )
                    }
                )
            )
        } }
    }

    override suspend fun getCategories(): List<SearchCategory> {
        return remote.getCategories().getResult { it.payload.categories.map { searchCategoriesResponse ->
            SearchCategory(
                id = searchCategoriesResponse.id,
                name = searchCategoriesResponse.name,
            )
        } }
    }

}