package com.example.flights

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flights.ui.FlightViewModel

object AppViewModelProvider{

    val factory = viewModelFactory {

        initializer {
            FlightViewModel(
                flightApplication().container.flightRepository
            )
        }
    }
}

fun CreationExtras.flightApplication(): FlightApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightApplication)