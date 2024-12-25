package com.example.flights.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "airports")
data class airport(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val iata: String,
    val name: String,
    val passengers: Int
)

@Entity(tableName = "favorite")
data class favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val departure_code: String,
    val destination_code: String
)

data class flightData(
    val depart_code: String,
    val depart: String,
    val arrival_code: String,
    val arrival: String,
    val isFavorite: Int = 0
)




