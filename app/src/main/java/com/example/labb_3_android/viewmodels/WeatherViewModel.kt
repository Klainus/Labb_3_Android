package com.example.labb_3_android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labb_3_android.R
import com.example.labb_3_android.api.WeatherResponse
import com.example.labb_3_android.api.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class WeatherViewModel : ViewModel() {

    // Use StateFlow for state management
    private val _temperature = MutableStateFlow("Loading...")
    val temperature: StateFlow<String> = _temperature

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _weatherIcon = MutableStateFlow(R.drawable.unknown_weather)
    val weatherIcon: StateFlow<Int> = _weatherIcon

    // Retrofit instance
    private val weatherService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherService::class.java)

    // Map the weather icon code to drawable resources
    private fun getIconResource(iconCode: String): Int {
        return when (iconCode) {
            "01d", "01n" -> R.drawable.clear_sky
            "02d", "02n" -> R.drawable.scattered_clouds
            "03d", "03n" -> R.drawable.scattered_clouds
            "04d", "04n" -> R.drawable.broken_clouds
            "09d", "09n" -> R.drawable.shower_rain
            "10d", "10n" -> R.drawable.rain
            "13d", "13n" -> R.drawable.snow
            else -> R.drawable.unknown_weather
        }
    }

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = weatherService.getWeather(city, apiKey)
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        _temperature.value = "Current temperature: ${weatherResponse.main.temp}°C"
                        _errorMessage.value = ""

                        val iconCode = weatherResponse.weather[0].icon
                        _weatherIcon.value = getIconResource(iconCode)
                    }
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                    _temperature.value = ""
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch data: ${e.localizedMessage}"
                _temperature.value = ""
            }
        }
    }
}
