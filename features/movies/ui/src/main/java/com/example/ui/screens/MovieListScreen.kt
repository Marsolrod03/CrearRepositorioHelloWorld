package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.MovieModel
import com.example.ui.R
import com.example.ui.view_model.Loader
import com.example.ui.view_model.MoviesState

@Composable
fun MovieListScreen(
    state: MoviesState,
    onClick: (MovieModel) -> Unit,
    onLoad: () -> Unit,
    onError: () -> Unit
) {
    when {
        state.loader == Loader.LoadingFull -> {
            LoadingFullScreenLottie()
        }

        state.errorMessage != null -> {
            onError()
        }

        state.succeedList.isNotEmpty() -> {
            LazyColumnMovies(
                state = state,
                onClick = onClick,
                onLoad = onLoad,
            )
        }
    }

}

@Composable
fun MovieItem(
    movieModel: MovieModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                onClickLabel = stringResource(
                    R.string.action_detail_movie,
                    movieModel.title
                )
            ) {
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(movieModel.poster_path),
                contentDescription = stringResource(R.string.poster_description, movieModel.title),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movieModel.title, fontWeight = FontWeight.Black, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movieModel.overview, fontWeight = FontWeight.Light, color = Color.White)
        }
    }
}

@Composable
fun LazyColumnMovies(
    state: MoviesState,
    onClick: (MovieModel) -> Unit,
    onLoad: () -> Unit,
) {
    val lazyColumState = rememberLazyListState()

    LazyColumn(
        state = lazyColumState,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(8.dp),
    ) {
        items(
            state.succeedList,
            key = { movie -> movie.id }
        ) { movieModel ->
            MovieItem(
                movieModel = movieModel,
                onClick = { onClick(movieModel) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            val lastVisibleItem = lazyColumState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            if (lastVisibleItem != null
                && lastVisibleItem >= state.succeedList.size - 5
                && state.loader != Loader.LoadingPartial
            ) {
                onLoad()
            }
        }
        if (state.loader == Loader.LoadingPartial) {
            item { LoadingPartialScreenLottie() }
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
        ),
        onClick = { }
    )
}

@Preview
@Composable
fun MovieListScreenPreview() {
    val sampleMovies = listOf(
        MovieModel(
            id = "1",
            overview = "Esto es la descripción UNO",
            poster_path = "",
            title = "Película UNO",
            backdrop_path = "",
            popularity = 1.0,
            release_date = "",
            vote_average = 1.0
        ),
        MovieModel(
            id = "2",
            overview = "Esto es la descripción DOS",
            poster_path = "",
            title = "Película DOS",
            backdrop_path = "",
            popularity = 1.0,
            release_date = "",
            vote_average = 1.0
        )
    )

    MovieListScreen(
        state = MoviesState(succeedList = sampleMovies),
        onError = {},
        onClick = {},
        onLoad = {},
    )
}
