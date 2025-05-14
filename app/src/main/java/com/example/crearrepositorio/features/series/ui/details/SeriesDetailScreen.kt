package com.example.crearrepositorio.features.series.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.crearrepositorio.features.series.domain.SerieModel

@Composable
fun SeriesDetailInfo(
    poster: String,
    title: String,
    dateEmision: String,
    overview: String,
    voteCount: String,
    voteAverage: String,
    detailsState: DetailsState,
    onError: () -> Unit
) {
    when (detailsState) {
        is DetailsState.Idle -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is DetailsState.Created -> {
            val details = detailsState.details

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(top = 72.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = poster,
                    contentDescription = details.name,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(56.dp))

                Text(
                    text = details.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = details.first_air_date,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = details.overview,
                    color = Color.White,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 76.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = details.vote_count.toString(),
                        color = Color.White,
                        modifier = Modifier.padding(start = 104.dp)
                    )
                    Text(
                        text = details.vote_average.toString(),
                        color = Color.White,
                        modifier = Modifier.padding(end = 104.dp)
                    )
                }
            }
        }

        is DetailsState.Error -> {
            onError()
        }
    }
}

@Preview
@Composable
private fun SeriesDetailInfoPreview() {
    SeriesDetailInfo(
        "",
        "Daredevil",
        "15/01/2025",
        "Un ciego muy fuerte",
        "",
        "",
        DetailsState.Created(SerieModel(12,"Daredevil","","",1.0,2,"01/02")),
        onError = {})

}
