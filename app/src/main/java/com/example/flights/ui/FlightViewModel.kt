package com.example.flights.ui

import androidx.lifecycle.ViewModel
import com.example.flights.data.airport
import com.example.flights.data.favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlightViewModel: ViewModel(){

    private val _uiState = MutableStateFlow(appUiData())
    val uiState: StateFlow<appUiData> =_uiState.asStateFlow()


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