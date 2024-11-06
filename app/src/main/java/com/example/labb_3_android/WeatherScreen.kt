package com.example.labb_3_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherScreen(cityName: String,
                  temperature: String,
                  weatherIcon: Int,
                  modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Weather in $cityName",
            fontSize = 38.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = temperature,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = weatherIcon),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }

    }