package com.example.crearrepositorio.features.films.data

import com.example.crearrepositorio.features.films.domain.MovieModel
import com.example.crearrepositorio.features.films.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl : MovieRepository {

    override fun createMovies(): Flow<List<MovieModel>> {

        val json_path =

       return flow {
            emit(
                listOf(
                    MovieModel("The Shawshank Redemption", "src1", listOf("Drama")),
                    MovieModel("The Dark Knight", "src2", listOf("Action", "Crime", "Drama")),
                    MovieModel("Inception", "src3", listOf("Action", "Adventure", "Sci-Fi")),
                    MovieModel("Forrest Gump", "src4", listOf("Drama", "Romance")),
                    MovieModel("The Matrix", "src5", listOf("Action", "Sci-Fi")),
                    MovieModel("Pulp Fiction", "src6", listOf("Crime", "Drama"))
                )
            )
        }
    }
}