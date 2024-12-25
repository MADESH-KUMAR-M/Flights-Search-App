package com.example.flights.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface airportDao {

    @Query("SELECT * from airports ORDER BY passengers desc")
    fun getALLAirports(): Flow<List<airport>>

}