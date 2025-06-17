package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.MovieModel
import com.example.ui.view_model.DetailMoviesState
import androidx.core.net.toUri
import com.example.movies.ui.R


@Composable
fun MovieDetailScreen(
    state: DetailMoviesState,
    onError: () -> Unit
) {
    val context = LocalContext.current

    if (state.errorMessage != null) {
        onError()
    } else {
        state.succeedMovie?.let {
            CreateDetailCard(it) { url ->
                context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
            }
        }
    }
}

@Composable
fun CreateDetailCard(movie: MovieModel, onImageClick: (String) -> Unit) {
    val movie: MovieModel = movie
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.backdrop_path),
            contentDescription = stringResource(R.string.background_description, movie.title),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clickable { onImageClick(movie.backdrop_path) }
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
            text = stringResource(R.string.popularity_description, movie.popularity),
            color = Color.White
        )
        Space()
        Text(
            text = stringResource(R.string.release_date_description, movie.release_date),
            color = Color.White
        )
        Space()
        Text(
            text = stringResource(R.string.vote_average_description, movie.vote_average),
            color = Color.White
        )
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
        state = DetailMoviesState(
            succeedMovie =
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

