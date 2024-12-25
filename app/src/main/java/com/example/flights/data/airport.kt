package com.example.flights.data

data class airport(
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




