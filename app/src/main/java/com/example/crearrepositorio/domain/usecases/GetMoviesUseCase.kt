package com.example.crearrepositorio.domain.usecases

import com.example.crearrepositorio.domain.entities.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesUseCase {

    fun getMovies(): Flow<List<MovieModel>>{
        return flow {
            emit(listOf(
                MovieModel("The Shawshank Redemption", "src1", listOf("Drama")),
                MovieModel("The Dark Knight", "src2", listOf("Action", "Crime", "Drama")),
                MovieModel("Inception", "src3", listOf("Action", "Adventure", "Sci-Fi")),
                MovieModel("Forrest Gump", "src4", listOf("Drama", "Romance")),
                MovieModel("The Matrix", "src5", listOf("Action", "Sci-Fi")),
                MovieModel("Pulp Fiction", "src6", listOf("Crime", "Drama"))
            ))
        }
    }
}