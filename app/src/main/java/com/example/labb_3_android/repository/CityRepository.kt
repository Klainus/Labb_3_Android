package com.example.labb_3_android.repository

import com.example.labb_3_android.api.WeatherService
import com.example.labb_3_android.database.City
import com.example.labb_3_android.database.CityDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CityRepository(
    private val cityDao: CityDao,
    private val weatherService: WeatherService,
    private val coroutineScope: CoroutineScope) {

    val allCities: Flow<List<City>> = cityDao.getAllCities()

    val favoriteCities: Flow<List<City>> = cityDao.getFavoriteCities()

    suspend fun insertCity(city: City) {
        withContext(Dispatchers.IO) {
            cityDao.insertCity(city)
        }
    }

    suspend fun updateCity(city: City) {
        withContext(Dispatchers.IO) {
            cityDao.updateCity(city)
        }
    }

    suspend fun deleteCity(city: City) {
        withContext(Dispatchers.IO) {
            cityDao.deleteCity(city)
        }
    }

    suspend fun toggleFavorite(city: City) {
        val updatedCity = city.copy(isFavorite = !city.isFavorite)
        updateCity(updatedCity)
    }
}
