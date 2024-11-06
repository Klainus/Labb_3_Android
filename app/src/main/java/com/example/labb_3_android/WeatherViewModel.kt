package com.example.labb_3_android

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.labb_3_android.api.WeatherResponse
import com.example.labb_3_android.api.WeatherService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {


    private val _temperature = mutableStateOf("Loading...")
    val temperature: State<String> = _temperature

    private val _errorMessage = mutableStateOf("Loading...")
    val errorMessage: State<String> = _errorMessage

    private val _weatherIcon = mutableStateOf(0)
    val weatherIcon: State<Int> = _weatherIcon

    private val weatherService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherService::class.java)

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
        weatherService.getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        val temp = weatherResponse.main.temp
                        _temperature.value = "Current temperature: $temp°C"
                        _errorMessage.value = ""

                        val iconCode = weatherResponse.weather[0].icon
                        _weatherIcon.value = getIconResource(iconCode)
                    }
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                    _temperature.value = ""
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _errorMessage.value = ""
                _temperature.value = "Failed to fetch data"
            }
        })
    }
}


