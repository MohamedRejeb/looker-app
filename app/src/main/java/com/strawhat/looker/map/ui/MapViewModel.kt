package com.strawhat.looker.map.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.strawhat.looker.location.domain.use_case.ClearRequestLocationUseCase
import com.strawhat.looker.location.domain.use_case.LocationFlowUseCase
import com.strawhat.looker.location.domain.use_case.RequestLocationUseCase
import com.strawhat.looker.map.domain.model.Place
import com.strawhat.looker.map.domain.repository.MapRepository
import com.strawhat.looker.review.domain.model.Review
import com.strawhat.looker.review.domain.repostiory.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mapRepository: MapRepository,
    private val reviewRepository: ReviewRepository,
    private val locationFlowUseCase: LocationFlowUseCase,
    private val requestLocationUseCase: RequestLocationUseCase,
    private val clearRequestLocationUseCase: ClearRequestLocationUseCase,
): ViewModel() {

    private val argPlaceId: String? = savedStateHandle["placeId"]

    var places by mutableStateOf(emptyList<Place>())
    var selectedPlaceId by mutableStateOf<String?>(null)
    var selectedProductId by mutableStateOf<String?>(null)

    var isLoading by mutableStateOf(false)
    var isSuccess by mutableStateOf(false)
    var isError by mutableStateOf(false)

    var isLocationPermissionDenied by mutableStateOf(false)

    var state by mutableStateOf(MapState())

    val location get() = locationFlowUseCase()
        .stateIn(
            scope = viewModelScope + Dispatchers.IO,
            started = SharingStarted.Eagerly,
            initialValue = LatLng(0.0, 0.0),
        )

    init {
        getPlaces()
        requestLocation()
    }

    fun requestLocation() {
        viewModelScope.launch {
            requestLocationUseCase()
            delay(1000)
            clearRequestLocationUseCase()
        }
    }

    private var getPlacesJob: Job? = null
    private fun getPlaces() {
        if (getPlacesJob?.isActive == true) return
        getPlacesJob = viewModelScope.launch {
            isLoading = true
            isError = false

            try {
                val places = mapRepository.getPlaces()
                this@MapViewModel.places = places
                isSuccess = true
            } catch (e: Exception) {
                e.printStackTrace()
                isError = true
            } finally {
                isLoading = false
                delay(200)
                Timber.d("placeId: $argPlaceId")
                argPlaceId?.let { selectedPlaceId = it }
            }
        }
    }

    var isUpdateProductStateLoading by mutableStateOf(false)
    var isUpdateProductStateSuccess by mutableStateOf(false)
    var isUpdateProductStateError by mutableStateOf(false)

    fun updateProductState(isAvailable: Boolean) {
        val placeId = selectedPlaceId ?: return
        val productId = selectedProductId ?: return
        val product = places
            .find { it.id == placeId }?.category?.products
            ?.find { it.id == productId }
        if (product?.isAvailable == isAvailable) return

        viewModelScope.launch {

            isUpdateProductStateLoading = true

            try {
                mapRepository.updateProductStatus(
                    productId = productId,
                    placeId = placeId,
                    isAvailable = isAvailable,
                )
                val places = this@MapViewModel.places.toMutableList()
                val placeIndex = places.indexOfFirst { it.id == placeId }
                val products = places[placeIndex].category.products.toMutableList()
                val productIndex = products.indexOfFirst { it.id == productId }
                val updatedProduct = products[productIndex].copy(
                    status = Place.Category.Product.getStatusFrom(isAvailable)
                )
                products[productIndex] = updatedProduct
                val updatedPlace = places[placeIndex].copy(
                    category = places[placeIndex].category.copy(
                        products = products
                    )
                )
                places[placeIndex] = updatedPlace
                this@MapViewModel.places = places
                isUpdateProductStateSuccess = true
            } catch(e: Exception) {
                e.printStackTrace()
                isUpdateProductStateError = true
            } finally {
                isUpdateProductStateLoading = false
            }

        }
    }

    var reviews by mutableStateOf<List<Review>>(emptyList())
    var reviewsCount by mutableStateOf(0)
    var reviewsAverage by mutableStateOf(0f)
    var selectedReviewId by mutableStateOf<String?>(null)

    var isGetReviewsLoading by mutableStateOf(false)

    fun getReviews() {
        val placeId = selectedPlaceId ?: return
        viewModelScope.launch {
            isGetReviewsLoading = true

            try {
                val reviewsData = reviewRepository.getReviewsData(placeId)

                reviews = reviewsData.reviews
                reviewsCount = reviewsData.count
                reviewsAverage = reviewsData.average
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                isGetReviewsLoading = false
            }
        }
    }

    var addReviewRate by mutableStateOf(0)
    var addReviewContent by mutableStateOf("")
    var isAddReviewLoading by mutableStateOf(false)

    fun addReview() {
        val placeId = selectedPlaceId ?: return
        val addReviewRate = addReviewRate.also { if (it == 0) return }
        val addReviewContent = addReviewContent.also { if (it.isEmpty()) return }
        viewModelScope.launch {
            isAddReviewLoading = true

            try {
                val review = reviewRepository.addReview(
                    placeId = placeId,
                    rate = addReviewRate,
                    content = addReviewContent,
                )

                val reviews = this@MapViewModel.reviews.toMutableList()
                reviews.add(0, review)
                this@MapViewModel.reviews = reviews

                this@MapViewModel.addReviewRate = 0
                this@MapViewModel.addReviewContent = ""
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                isAddReviewLoading = false
            }
        }
    }

    var isEditReviewActive by mutableStateOf(false)
    var isEditReviewLoading by mutableStateOf(false)

    fun updateReview() {
        val placeId = selectedPlaceId ?: return
        val reviewRate = addReviewRate.also { if (it == 0) return }
        val reviewContent = addReviewContent.also { if (it.isEmpty()) return }
        viewModelScope.launch {
            isEditReviewLoading = true

            try {
                val review = reviewRepository.updateReview(
                    placeId = placeId,
                    rate = reviewRate,
                    content = reviewContent,
                )

                val reviews = this@MapViewModel.reviews.toMutableList()
                reviews[0] = review
                this@MapViewModel.reviews = reviews

                this@MapViewModel.isEditReviewActive = false
                this@MapViewModel.addReviewRate = 0
                this@MapViewModel.addReviewContent = ""
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                isEditReviewLoading = false
            }
        }
    }

    var isDeleteReviewLoading by mutableStateOf(false)

    fun deleteReview() {
        val placeId = selectedPlaceId ?: return
        viewModelScope.launch {
            isDeleteReviewLoading = true

            try {
                reviewRepository.deleteReview(placeId = placeId)

                val reviews = this@MapViewModel.reviews.toMutableList()
                reviews.removeAt(0)
                this@MapViewModel.reviews = reviews
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                isDeleteReviewLoading = false
            }
        }
    }
}