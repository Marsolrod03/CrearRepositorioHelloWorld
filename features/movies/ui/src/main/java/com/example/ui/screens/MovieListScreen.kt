package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.MovieModel
import com.example.ui.view_model.MoviesState

@Composable
fun MovieListScreen(
    state: MoviesState,
    onError: () -> Unit
) {
    when (state) {
        MoviesState.Idle -> Unit
        is MoviesState.Succeed -> {
            val movieList = state.movies
            LazyColumn(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(movieList) { movieModel ->
                    MovieItem(movieModel)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        is MoviesState.Loader.LoadingFirstTime -> {

        }

        is MoviesState.Loader.LoadingMoreMovies -> {

        }

        is MoviesState.Error -> {
            LaunchedEffect(Unit) {
                onError()
            }
        }
    }
}

@Composable
fun MovieItem(movieModel: MovieModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(movieModel.poster_path),
                contentDescription = "Poster image from ${movieModel.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movieModel.title, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movieModel.overview, fontWeight = FontWeight.Light)
        }
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(
        movieModel = MovieModel(
            id = 1.toString(),
            overview = "Esto es la descripcion",
            poster_path = "",
            title = "Título de prueba",
            backdrop_path = "",
            popularity = 0.0,
            release_date = "",
            vote_average = 0.0
        )
    )
}

@Preview
@Composable
fun MovieListScreenPreview() {
    val sampleMovies = listOf(
        MovieModel(
            id = "1",
            overview = "Una película de acción emocionante con giros inesperados.",
            poster_path = "",
            title = "Película de Acción",
            backdrop_path = "",
            popularity = 1.0,
            release_date = "",
            vote_average = 1.0
        ),
        MovieModel(
            id = "2",
            overview = "Una conmovedora historia de amor en tiempos difíciles.",
            poster_path = "",
            title = "Drama Romántico",
            backdrop_path = "",
            popularity = 1.0,
            release_date = "",
            vote_average = 1.0
        )
    )

    MovieListScreen(
        state = MoviesState.Succeed(sampleMovies),
        onError = {}
    )
}
