package com.example.labb_3_android.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.labb_3_android.viewmodels.SearchViewmodel
import com.example.labb_3_android.database.City

@Composable
fun SearchScreen(viewModel: SearchViewmodel, onCitySelected: (String) -> Unit) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    val cities by viewModel.allCities.collectAsState(initial = emptyList())

    val filteredCities = cities.filter {
        it.cityName.contains(query.text, ignoreCase = true)
    }

    var newCityName by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        BasicTextField(
            value = query,
            onValueChange = { query = it },
            keyboardActions = KeyboardActions(onDone = { /* Handle search logic */ }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (query.text.isEmpty()) {
                        Text("Search for a city", style = MaterialTheme.typography.bodyLarge)
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = newCityName,
            onValueChange = { newCityName = it },
            keyboardActions = KeyboardActions(onDone = { /* Handle adding new city */ }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (newCityName.text.isEmpty()) {
                        Text("Enter a new city", style = MaterialTheme.typography.bodyLarge)
                    }
                    innerTextField()
                }
            }
        )

        Button(
            onClick = {
                if (newCityName.text.isNotEmpty()) {
                    val city = City(cityName = newCityName.text, isFavorite = false)
                    viewModel.addCity(city)
                    newCityName = TextFieldValue("")
                }
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Add City")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredCities.isEmpty()) {
            Text(
                text = "No cities found",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(filteredCities) { city ->
                    CityItem(city = city, onCitySelected = onCitySelected, onToggleFavorite = { viewModel.toggleFavorite(city) }, onDelete = { viewModel.deleteCity(city) })
                }
            }
        }
    }
}

@Composable
fun CityItem(city: City, onCitySelected: (String) -> Unit, onToggleFavorite: (City) -> Unit, onDelete: (City) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCitySelected(city.cityName) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = city.cityName)

        Row {
            IconButton(onClick = { onToggleFavorite(city) }) {
                Text(text = if (city.isFavorite) "Unfavorite" else "Favorite")
            }
            IconButton(onClick = { onDelete(city) }) {
                Text(text = "Delete")
            }
        }
    }
}
