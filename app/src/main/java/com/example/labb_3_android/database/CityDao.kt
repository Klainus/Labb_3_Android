package com.example.labb_3_android.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Query("SELECT * FROM city_table ORDER BY timestamp DESC")
    fun getAllCities(): Flow<List<City>>

    @Query("SELECT * FROM city_table WHERE isFavorite = 1")
    fun getFavoriteCities(): Flow<List<City>>

    @Update
    suspend fun updateCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)

    @Query("SELECT * FROM city_table WHERE cityName = :name LIMIT 1")
    suspend fun getCityByName(name: String): City?


    @Query("UPDATE city_table SET isFavorite = NOT isFavorite WHERE cityName = :name")
    suspend fun toggleFavoriteStatus(name: String)
}
