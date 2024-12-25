package com.example.flights.data

import kotlinx.coroutines.flow.Flow

class FlightRepository(private val dao: airportDao) {

    fun getAllAirport() : Flow<List<airport>> = dao.getALLAirports()

    fun getSuggest(keyword: String) : Flow<List<airport>> = dao.getSuggestion(keyword)

    fun getAllFights(code:String, name: String) : Flow<List<flightData>> = dao.getAllFlights(code,name)

    suspend fun insert(favorite: favorite) {
        val isExist = dao.favoriteIsExists(favorite.departure_code, favorite.destination_code) > 0
        if (!isExist){
            dao.insert(favorite)
        }
    }

    suspend fun delete(favorite: favorite){
        val id = dao.favoriteIsExists(favorite.departure_code, favorite.destination_code)
        dao.deleteFavorite(id)
    }

    suspend fun getAllFavorites(): Flow<List<flightData>> = dao.getAllFavorites()
}