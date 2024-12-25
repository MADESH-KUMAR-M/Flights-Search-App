package com.example.flights

import android.app.Application
import com.example.flights.data.AppContainer
import com.example.flights.data.AppDataContainer

class FlightApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}