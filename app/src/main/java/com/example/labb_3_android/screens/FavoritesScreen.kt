package com.example.labb_3_android.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.labb_3_android.database.City
import com.example.labb_3_android.viewmodels.SearchViewmodel

@Composable
fun FavoritesScreen(viewModel: SearchViewmodel, onCitySelected: (String) -> Unit) {

    val favoriteCities = viewModel.favoriteCities.collectAsState(initial = emptyList()).value

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorites",
            modifier = Modifier.padding(16.dp)
        )

        if (favoriteCities.isEmpty()) {
            Text(
                text = "No favorite cities yet!",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favoriteCities) { city ->
                    CityItem(
                        city = city,
                        onClick = { onCitySelected(city.cityName) },
                        onFavoriteClick = { viewModel.toggleFavorite(city) }
                    )
                }
            }
        }
    }
}
@Composable
fun CityItem(city: City,
             onClick: () -> Unit,
             onFavoriteClick: (City) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city.cityName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )


        IconButton(onClick = { onFavoriteClick(city) }) {
            Icon(
                imageVector = if (city.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite"
            )
        }
    }
}
