package com.example.crearrepositorio.features.films.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.ui.view_model.DetailMoviesState

@Composable
fun MovieDetailScreen(
    state: DetailMoviesState,
    onError: () -> Unit
) {
    when (state) {
        DetailMoviesState.Idle -> Unit
        is DetailMoviesState.Succeed -> {
            val movie = state.movies
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.backdrop_path),
                    contentDescription = "Background image from ${movie.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                )
                Space()
                Text(
                    text = movie.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Space()
                Text(
                    text = movie.overview,
                    color = Color.White
                )
                Space()
                Text(
                    text = "Popularity: ${movie.popularity}",
                    color = Color.White
                )
                Space()
                Text(
                    text = "Release Date: ${movie.release_date}",
                    color = Color.White
                )
                Space()
                Text(
                    text = "Vote Average: ${movie.vote_average}",
                    color = Color.White
                )
            }
        }

        is DetailMoviesState.Error -> {
            LaunchedEffect(Unit) {
                onError()
            }
        }
    }
}

@Composable
fun Space() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen(
        state = DetailMoviesState.Succeed(
            MovieModel(
                id = "1",
                title = "ejemplo",
                backdrop_path = "",
                overview = "ejemplo",
                popularity = 10.0,
                release_date = "2001-04-26",
                vote_average = 10.0,
                poster_path = ""
            )
        ),
        onError = {}
    )
}

