package com.example.labb_3_android.viewmodels

import androidx.lifecycle.ViewModel
import com.example.labb_3_android.database.CityDao
import com.example.labb_3_android.database.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class SearchViewmodel : ViewModel() {

    private lateinit var cityDao: CityDao

    val allCities: Flow<List<City>> = cityDao.getAllCities()
    val favoriteCities: Flow<List<City>> = cityDao.getFavoriteCities()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun addCity(city: City) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                cityDao.insertCity(city)
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error adding city: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun updateCity(city: City) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                cityDao.updateCity(city)
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error updating city: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun deleteCity(city: City) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                cityDao.deleteCity(city)
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting city: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(city: City) {
        val updatedCity = city.copy(isFavorite = !city.isFavorite)
        updateCity(updatedCity)
    }
}
