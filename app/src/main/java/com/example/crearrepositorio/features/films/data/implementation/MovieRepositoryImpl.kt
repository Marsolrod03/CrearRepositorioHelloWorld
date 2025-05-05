package com.example.crearrepositorio.features.films.data.implementation

import com.example.crearrepositorio.features.films.data.dataSource.MoviesNetworkDataSource
import com.example.crearrepositorio.features.films.data.mapper.toMovieModel
import com.example.crearrepositorio.features.films.domain.MovieWrapper
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl @Inject constructor(
    private val networkDataSource: MoviesNetworkDataSource
): MovieRepository {

    private var currentPage = 1
    private var listChange: MutableList<MovieModel> = mutableListOf()

    override fun createMovies(): Flow<Result<MovieWrapper>> = flow {
        try {
            val pagedResult = networkDataSource.fetchPopularMovies(currentPage)
            pagedResult?.let {
                val movieList = it.results.map {movie -> movie.toMovieModel()}
                listChange.addAll(movieList)
                val hasMorePages = it.page < it.total_pages
                val movieWrapper = MovieWrapper(hasMorePages, listChange)
                currentPage ++
                emit(Result.success(movieWrapper))
            }?: run {
                emit(Result.success(MovieWrapper(false, emptyList())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }
}