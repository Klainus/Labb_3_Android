package com.example.labb_3_android.api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.labb_3_android.ui.theme.Labb_3_AndroidTheme
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherService = retrofit.create<WeatherService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Labb_3_AndroidTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Stockholm",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                var temperature by remember { mutableStateOf("Loading...") }
                fetchWeather("Stockholm", "c9097b15b320ddb685ada35f541bf654") { temp ->
                    temperature = "Current temperature: $temp°C"
                }
            }
        }
    }


    private fun fetchWeather(city: String, apiKey: String, callback: (String) -> Unit) {
        weatherService.getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                response.body()?.let { weatherResponse ->
                    val temperature = weatherResponse.main.temp
                    callback(temperature.toString())
                    val description = weatherResponse.weather[0].description
                    println("Temperature: $temperature")
                    println("Weather description: $description")
                }
            }

            }

            override fun onFailure(
                call: Call<WeatherResponse?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        }
        )
    }
}

private fun MainActivity.Greeting(name: String, modifier: Modifier) {}


/**  response.body()?.main?.temp?.let {
        callback(it.toString())
    } ?:callback("Error: Temperature data not found")
} else {
    callback("Error: ${response.message()}")
} **/