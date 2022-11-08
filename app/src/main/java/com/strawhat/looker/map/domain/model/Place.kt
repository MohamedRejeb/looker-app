package com.strawhat.looker.map.domain.model

import com.strawhat.looker.R

data class Place(
    val id: String,
    val name: String,
    val lat: Double,
    val long: Double,
    val category: Category,
    val content: String,
    val slides: List<Slide> = emptyList(),
) {
    data class Slide(
        val id: String,
        val image: String,
    )

    data class Category(
        val id: String,
        val name: String,
        val image: String,
        val products: List<Product>,
    ) {
        val iconResourceId = when(name.lowercase()) {
            "marker" -> R.drawable.market
            "gas station" -> R.drawable.gas_station
            "pharmacy" -> R.drawable.pharmacy
            "hospital" -> R.drawable.hospital
            "bakery" -> R.drawable.bakery
            else -> R.drawable.market
        }

        data class Product(
            val id: String,
            val name: String,
            val image: String,
            val status: Status = Status.Unknown,
        ) {
            val statusText = when(status) {
                Status.Available -> "Available"
                Status.Unavailable -> "Not available"
                Status.Unknown -> "Unknown"
            }

            val isAvailable: Boolean? = when(status) {
                Status.Available -> true
                Status.Unavailable -> false
                Status.Unknown -> null
            }

            enum class Status {
                Available, Unavailable, Unknown
            }

            companion object {
                fun getStatusFrom(isAvailable: Boolean?): Status = when(isAvailable) {
                    true -> Status.Available
                    false -> Status.Unavailable
                    null -> Status.Unknown
                }
            }
        }
    }
}