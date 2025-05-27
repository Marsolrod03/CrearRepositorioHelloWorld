package com.example.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ActorDetailsScreen(
    actorImage: String?,
    actorGender: String?,
    detailsState: DetailsState,
    onError: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = actorImage,
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .clip(RectangleShape),
            contentScale = ContentScale.Crop
        )

        if(detailsState.isIdle){
        }else if(detailsState.errorMessage != null){
            LaunchedEffect(Unit) {
                onError()
            }
        }else if(detailsState.details != null){
            val details = detailsState.details

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = details.name,
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            Text(
                text = "GENDER: $actorGender",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White
                )
            )

            Text(
                text = "POPULARITY: ${details.popularity}",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = details.biography,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .weight(0.6f)
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}