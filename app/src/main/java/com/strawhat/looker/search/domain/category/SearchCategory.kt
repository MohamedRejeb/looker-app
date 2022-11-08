package com.strawhat.looker.search.domain.category

import com.strawhat.looker.R

data class SearchCategory(
    val id: String,
    val name: String
) {
    val iconResourceId = when(name) {
        "marker" -> R.drawable.market
        "gas station" -> R.drawable.gas_station
        "pharmacy" -> R.drawable.pharmacy
        "hospital" -> R.drawable.hospital
        "bakery" -> R.drawable.bakery
        else -> R.drawable.market
    }
}
