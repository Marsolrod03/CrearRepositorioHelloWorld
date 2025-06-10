package com.example.data.implementation

import com.example.domain.MovieWrapper
import com.example.domain.repository.MovieRepository
import com.example.data.mapper.toMovieModel
import com.example.data.mapper.toMovieEntity
import com.example.data.dataSource.MoviesLocalDataSource
import com.example.data.dataSource.MoviesNetworkDataSource
import com.example.domain.model.MovieModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl @Inject constructor(
    private val networkDataSource: MoviesNetworkDataSource,
    private val localDataSource: MoviesLocalDataSource
) : MovieRepository {
    var accumulatedMovies: MutableList<MovieModel> = mutableListOf()
    var currentPage = 1

    override fun manageMoviesPagination(): Flow<Result<MovieWrapper>> = flow {
        try {
            var lastMoviePageInDatabase = getLastMoviePage()
            val databaseMovies = getAllMoviesFromDatabase()
            val totalPages = getTotalPages()

            println("accumulatedMovies: ${accumulatedMovies.size}, databaseMovies: ${databaseMovies.size}")

            if (accumulatedMovies.isEmpty() && databaseMovies.isNotEmpty()) {
                currentPage = lastMoviePageInDatabase + 1
                val hasMorePages = currentPage <= totalPages
                accumulatedMovies.addAll(databaseMovies)
                emit(Result.success(MovieWrapper(hasMorePages, accumulatedMovies, totalPages)))
            } else {
                getAllMoviesFromApi()
                    .collect { result ->
                        result.onSuccess { movieWrapper ->
                            insertAllMovies(movieWrapper.movieList)
                            updateTotalPages(movieWrapper.totalPages)
                            if (lastMoviePageInDatabase == 0) {
                                insertPagination()
                            }
                            updateLastLoadedPage(currentPage)
                            emit(Result.success(movieWrapper))
                            currentPage++
                        }
                        result.onFailure {
                            emit(Result.success(MovieWrapper(false, databaseMovies, totalPages)))
                        }
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    override fun manageMovieDetails(movieId: Int): Flow<Result<MovieModel>> = flow {
        try {
            getDetailMoviesFromApi(movieId)
                .collect { result ->
                    result.onSuccess { movieModel ->
                        emit(Result.success(movieModel))
                    }
                    result.onFailure {
                        emit(Result.success(getDetailMoviesFromDatabase(movieId)))
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    //tested
    override suspend fun clearAndUpdateDatabase(timeRN: Long) {
        clearMovieDatabase()
        deleteAllMoviePages()
        insertPagination()
        updateLastDelete(timeRN)
    }

    private fun getAllMoviesFromApi(): Flow<Result<MovieWrapper>> = flow {
        try {
            var totalPages = Int.MAX_VALUE
            val pagedResult = networkDataSource.fetchPopularMovies(currentPage)
            pagedResult?.let {
                val movieList = it.results.map { movie -> movie.toMovieModel() }
                val newMovies = movieList.filter { apiMovie ->
                    accumulatedMovies.none { it.id == apiMovie.id }
                }
                accumulatedMovies.addAll(newMovies)
                totalPages = it.total_pages
                val hasMorePages = it.page < totalPages
                val movieWrapper = MovieWrapper(hasMorePages, accumulatedMovies, totalPages)
                emit(Result.success(movieWrapper))
            } ?: run {
                emit(Result.success(MovieWrapper(false, emptyList(), totalPages)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    private suspend fun getAllMoviesFromDatabase(): List<MovieModel> {
        val movieListDB = localDataSource.getAllMovies().map { it.toMovieModel() }
        val newMovies = movieListDB.filter { dbMovie ->
            accumulatedMovies.none { it.id == dbMovie.id }
        }
        accumulatedMovies.addAll(newMovies)
        return accumulatedMovies
    }

    private suspend fun insertAllMovies(movies: List<MovieModel>) =
        localDataSource.insertAllMovies(movies.map { it.toMovieEntity() })

    //tested
    private suspend fun insertPagination() =
        localDataSource.insertPagination()

    //tested
    private suspend fun clearMovieDatabase() =
        localDataSource.clearDatabase()

    private fun getDetailMoviesFromApi(movieId: Int): Flow<Result<MovieModel>> = flow {
        try {
            val detailMovie = networkDataSource.fetchDetailMovies(movieId)
            detailMovie?.let {
                emit(Result.success(it.toMovieModel()))
            } ?: run {
                emit(Result.failure(Exception("Error loading movie details")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    private suspend fun getDetailMoviesFromDatabase(movieId: Int): MovieModel =
        localDataSource.getMovieDetails(movieId).toMovieModel()

    private suspend fun getLastMoviePage(): Int =
        localDataSource.getLastMoviePage()

    //tested
    private suspend fun deleteAllMoviePages() =
        localDataSource.deleteAllMoviePages()

    private suspend fun updateLastLoadedPage(lastLoadedPage: Int) =
        localDataSource.updateLastLoadedPage(lastLoadedPage)

    override suspend fun getLastDelete() =
        localDataSource.getLastDeleteDB()

    //tested
    private suspend fun updateLastDelete(lastDelete: Long) =
        localDataSource.updateLastDeleteDB(lastDelete)

    private suspend fun getTotalPages(): Int =
        localDataSource.getTotalPages()

    private suspend fun updateTotalPages(totalPages: Int) =
        localDataSource.updateTotalPages(totalPages)
}