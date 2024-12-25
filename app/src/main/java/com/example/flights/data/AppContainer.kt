package com.example.flights.data

import android.content.Context

interface AppContainer {

    val flightRepository:FlightRepository
}

class AppDataContainer( private  val context: Context) : AppContainer{

    override val flightRepository: FlightRepository by lazy {
        FlightRepository(FlightDatabase.getDatabase(context).airportDao())
    }
}