package com.example.flights.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flights.data.FlightRepository
import com.example.flights.data.airport
import com.example.flights.data.favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlightViewModel(private  val flightRepository: FlightRepository): ViewModel(){

    private val _uiState = MutableStateFlow(appUiData())

    val uiState:StateFlow<appUiData> = flightRepository.getAllAirport().map { appUiData(searchList = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = appUiData()
        )


    fun fetchAirports(){
        viewModelScope.launch {
            val airports = flightRepository.getAllAirport()

        }
    }
}

data class appUiData(
    val isSelected: Boolean = true,
    val airPortName: String = "",
    val searchList: List<airport> = listOf(
        airport(
            id = 1,
            iata = "JTC",
            name = "Jolarpetaai",
            passengers = 10
        )
    ),
    val favoriteList: List<favorite> = listOf()
)

