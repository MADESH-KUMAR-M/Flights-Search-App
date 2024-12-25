package com.example.flights.data

import kotlinx.coroutines.flow.Flow

class FlightRepository(private val dao: airportDao) {

    fun getAllAirport() : Flow<List<airport>> = dao.getALLAirports()

}