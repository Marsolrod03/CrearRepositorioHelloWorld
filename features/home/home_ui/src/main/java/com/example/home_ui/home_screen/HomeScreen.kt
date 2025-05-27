package com.example.home_ui.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.home_ui.HomeState
import com.example.home_ui.NavigationTarget
import com.example.home_ui.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: State<HomeState>,
    onNavigate: (NavigationTarget) -> Unit,
    onNavigationComplete: () -> Unit
) {
    LaunchedEffect(uiState.navigateTo) {
        uiState.navigateTo?.let {
            onNavigate(it)
            onNavigationComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text("Inicio", color = Color.White) },
            modifier = Modifier.background(Color.Black)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = R.drawable.icon2),
            contentDescription = "Logo",
            modifier = Modifier
                .height(347.dp)
                .width(290.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onNavigate(NavigationTarget.FILMS) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Películas", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(NavigationTarget.SERIES) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Series", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(NavigationTarget.ACTORS) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actores", fontWeight = FontWeight.Bold)
        }
    }
}