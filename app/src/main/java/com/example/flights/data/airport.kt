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

data class favorite(
    val id: Int,
    val departure_code: String,
    val destination_code: String
)




