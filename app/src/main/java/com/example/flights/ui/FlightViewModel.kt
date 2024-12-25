package com.example.flights.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flights.data.FlightRepository
import com.example.flights.data.airport
import com.example.flights.data.favorite
import com.example.flights.data.flightData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightViewModel(private  val flightRepository: FlightRepository): ViewModel(){

    private val _uiState = MutableStateFlow(appUiData())

    val uiState:StateFlow<appUiData> get() = _uiState

    init {
        viewModelScope.launch {
            flightRepository.getAllFavorites()
                .stateIn(
                    viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = listOf()
                ).collect{ flight ->
                    _uiState.update {
                        it.copy(flightList = flight)
                    }
                }
        }
    }

    fun fetchAirports(keyword: String) {
        viewModelScope.launch {
            flightRepository.getSuggest(keyword = keyword)
                .stateIn(
                    viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = listOf()
                ).collect { airports ->
                    _uiState.update {
                        it.copy(searchList = airports)
                    }
                }

        }
    }

    fun updateIsSelected(state: Boolean){
        _uiState.update {
            it.copy(isSelected = state)
        }
    }

    fun fetchAllFlights(code: String, name: String){
        viewModelScope.launch {
            flightRepository.getAllFights(code, name)
                .stateIn(
                    viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = listOf()
                )
                .collect{ flights->
                    _uiState.update {
                        it.copy(flightList = flights)
                    }
                }
        }
    }

    fun saveFavorite(depart_code: String, destination_code: String){
        val favorite: favorite = favorite(departure_code = depart_code, destination_code = destination_code)
        viewModelScope.launch {
            flightRepository.insert(favorite = favorite)
        }
    }

    fun deleteFavorite(depart_code: String, destination_code: String){
        val favorite: favorite = favorite(departure_code = depart_code, destination_code = destination_code)
        viewModelScope.launch {
            flightRepository.delete(favorite)
        }
    }
}


    data class appUiData(
        val isSelected: Boolean = false,
        val searchList: List<airport> = emptyList(),
        val flightList: List<flightData> = listOf()
)

