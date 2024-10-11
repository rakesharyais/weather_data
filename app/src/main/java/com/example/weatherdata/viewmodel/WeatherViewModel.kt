package com.example.weatherdata.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdata.model.WeatherResponse
import com.example.weatherdata.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    // Holds the weather data of the searched city
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData


    //Fetches the weather details for a given city
    fun fetchWeather(cityName: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val geoData = repository.getCoordinates(cityName, apiKey)
                geoData?.let {
                    val weather = repository.getWeatherDetails(it.lat, it.lon, apiKey)
                    // Update the weather data which will be observed by the ui
                    _weatherData.value = weather
                }
            } catch (e: Exception) {
                Log.d("WeatherViewModel","Exception = ${e.message}")
            }
        }
    }
}