package com.strawhat.looker.map.data.repository

import com.strawhat.looker.map.data.remote.MapRemoteDataSource
import com.strawhat.looker.map.data.remote.UpdateProductStatusRequest
import com.strawhat.looker.map.domain.model.Place
import com.strawhat.looker.map.domain.repository.MapRepository
import com.strawhat.looker.utils.error_handling.getResult
import java.util.UUID
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val remote: MapRemoteDataSource,
): MapRepository {

    override suspend fun getPlaces(): List<Place> {
        return remote.getPlaces().getResult { it.payload.places.map { placeResponse ->
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

    override suspend fun updateProductStatus(
        productId: String,
        placeId: String,
        isAvailable: Boolean
    ) {
        remote.updateProductStatus(
            UpdateProductStatusRequest(
                product = productId,
                place = placeId,
                isAvailable = isAvailable,
            )
        ).getResult { }
    }

}