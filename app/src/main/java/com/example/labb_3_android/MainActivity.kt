package com.example.labb_3_android


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.labb_3_android.screens.CityWeatherScreen
import com.example.labb_3_android.screens.FavoritesScreen
import com.example.labb_3_android.screens.SearchScreen
import com.example.labb_3_android.ui.theme.Labb_3_AndroidTheme
import com.example.labb_3_android.viewmodels.SearchViewmodel
import com.example.labb_3_android.viewmodels.WeatherViewModel
import com.example.labb_3_android.database.AppDatabase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Labb_3_AndroidTheme {
                val navController = rememberNavController()



                val searchViewModel: SearchViewmodel = viewModel()
                val weatherViewModel: WeatherViewModel = viewModel()


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "weather/Stockholm"
                    ) {
                        composable("weather/{cityName}") { backStackEntry ->
                            val cityName = backStackEntry.arguments?.getString("cityName") ?: "Stockholm"
                            CityWeatherScreen(
                                cityName = cityName,
                                weatherViewModel = weatherViewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("favorites") {
                            FavoritesScreen(
                                viewModel = searchViewModel,
                                onCitySelected = { cityName ->
                                    navController.navigate("weather/$cityName")
                                })
                        }
                        composable("search") {
                            SearchScreen(
                                viewModel = searchViewModel,
                                onCitySelected = { cityName ->
                                    navController.navigate("weather/$cityName")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { navController.navigate("weather/Stockholm") }) {
            Text("Weather")
        }
        Button(onClick = { navController.navigate("favorites") }) {
            Text("Favorites")
        }
        Button(onClick = { navController.navigate("search") }) {
            Text("Search")
        }
    }
}
