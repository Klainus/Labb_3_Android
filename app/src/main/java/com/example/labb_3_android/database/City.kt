package com.example.labb_3_android.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class City(
    @PrimaryKey val cityName: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
