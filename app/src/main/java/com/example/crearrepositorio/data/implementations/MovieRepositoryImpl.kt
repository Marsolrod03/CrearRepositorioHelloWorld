package com.example.crearrepositorio.data.implementations

import com.example.crearrepositorio.domain.entities.MovieModel
import com.example.crearrepositorio.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl : MovieRepository {

    override fun createMovies(): Flow<List<MovieModel>> {
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