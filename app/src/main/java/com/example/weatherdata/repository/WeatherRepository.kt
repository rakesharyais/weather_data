package com.example.weatherdata.repository

import com.example.weatherdata.api.WeatherApi
import com.example.weatherdata.model.GeoLocationResponse
import com.example.weatherdata.model.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    //to fetches the weather details of a city
    suspend fun getWeatherDetails(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResponse? {
        val response = api.getWeatherDetails(latitude, longitude, apiKey)
        return if (response.isSuccessful) {
            response.body() // Return the weather response
        } else {
            //Log.e("WeatherRepository", "Failed to fetch weather details: ${response.errorBody()}")
            null
        }
    }

    //Fetches the latitude and longitude for a given city name.
    suspend fun getCoordinates(cityName: String, apiKey: String): GeoLocationResponse? {
        val response = api.getCoordinates(cityName, 1, apiKey)
        return if (response.isSuccessful) {
            val geoLocations = response.body()
            geoLocations?.firstOrNull() // Return the first result, or null if empty
        } else {
            //Log.e("WeatherRepository", "Failed to fetch coordinates: ${response.errorBody()}")
            null
        }
    }
}