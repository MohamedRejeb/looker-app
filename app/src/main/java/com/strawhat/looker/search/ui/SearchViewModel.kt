package com.strawhat.looker.search.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.strawhat.looker.location.domain.use_case.LocationFlowUseCase
import com.strawhat.looker.location.domain.use_case.RequestLocationUseCase
import com.strawhat.looker.map.domain.model.Place
import com.strawhat.looker.search.domain.category.SearchCategory
import com.strawhat.looker.search.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val locationFlowUseCase: LocationFlowUseCase,
    private val requestLocationUseCase: RequestLocationUseCase,
): ViewModel() {

    init {
        getCategories()

        viewModelScope.launch {
            requestLocationUseCase()

            locationFlowUseCase().collectLatest { location ->
                Timber.d("location $location")
                if (location.latitude != 0.0) {
                    search()
                }
            }
        }
    }

    val location get() = locationFlowUseCase()
        .stateIn(
            scope = viewModelScope + Dispatchers.IO,
            started = SharingStarted.Eagerly,
            initialValue = LatLng(0.0, 0.0),
        )

    var places by mutableStateOf<List<Place>>(emptyList())

    var categoryId by mutableStateOf<String?>(null)
    var value by mutableStateOf<String?>(null)
    var isAvailable by mutableStateOf<Boolean?>(null)

    var isLoading by mutableStateOf(false)

    private var searchJob: Job? = null
    fun search() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (places.isNotEmpty()) delay(200)
            isLoading = true

            try {
                val places = searchRepository.search(
                    lat = location.value.latitude,
                    long = location.value.longitude,
                    categoryId = categoryId,
                    value = value,
                    isAvailable = isAvailable,
                )

                this@SearchViewModel.places = places
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    var categories by mutableStateOf<List<SearchCategory>>(emptyList())

    fun getCategories() {
        viewModelScope.launch {
            try {
                val categories = searchRepository.getCategories()
                this@SearchViewModel.categories = categories
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }

}