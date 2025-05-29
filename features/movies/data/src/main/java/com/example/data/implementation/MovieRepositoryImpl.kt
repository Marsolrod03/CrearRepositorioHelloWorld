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
    private var accumulatedMovies: MutableList<MovieModel> = mutableListOf()
    var currentPage = 1

    override fun manageMoviesPagination(): Flow<Result<MovieWrapper>> = flow {
        try {
            var lastMoviePageInDatabase = getLastMoviePage()
            val databaseMovies = getAllMoviesFromDatabase()
            val totalPages = getTotalPages()

//            if (lastMoviePageInDatabase > currentPage) {
//                currentPage = lastMoviePageInDatabase
//                val hasMorePages = currentPage < totalPages
//                emit(Result.success(MovieWrapper(hasMorePages, databaseMovies, totalPages)))
//            }
            if (accumulatedMovies.isEmpty() && databaseMovies.isNotEmpty()) {
                currentPage = lastMoviePageInDatabase + 1
                val hasMorePages = currentPage <= totalPages
                accumulatedMovies.addAll(databaseMovies)
                emit(Result.success(MovieWrapper(hasMorePages, accumulatedMovies, totalPages)))
            } else {
                getAllMoviesFromApi(currentPage)
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

    override suspend fun clearAndUpdateDatabase(timeRN: Long) {
        clearMovieDatabase()
        deleteAllMoviePages()
        updateLastDelete(timeRN)
    }

    //cambiar
    override fun getAllMoviesFromApi(page: Int): Flow<Result<MovieWrapper>> = flow {
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

    //cambiar
    override suspend fun getAllMoviesFromDatabase(): List<MovieModel> {
        val movieListDB = localDataSource.getAllMovies().map { it.toMovieModel() }
        val newMovies = movieListDB.filter { dbMovie ->
            accumulatedMovies.none { it.id == dbMovie.id }
        }
        accumulatedMovies.addAll(newMovies)
        return accumulatedMovies
    }


    //cambiar
    override suspend fun insertAllMovies(movies: List<MovieModel>) =
        localDataSource.insertAllMovies(movies.map { it.toMovieEntity() })

    override suspend fun insertPagination() {
        localDataSource.insertPagination()
    }

    override suspend fun clearMovieDatabase() =
        localDataSource.clearDatabase()

    override fun getDetailMoviesFromApi(movieId: Int): Flow<Result<MovieModel>> = flow {
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

    override suspend fun getDetailMoviesFromDatabase(movieId: Int): MovieModel =
        localDataSource.getMovieDetails(movieId).toMovieModel()

    override suspend fun getLastMoviePage(): Int =
        localDataSource.getLastMoviePage()

    override suspend fun deleteAllMoviePages() =
        localDataSource.deleteAllMoviePages()

    override suspend fun updateLastLoadedPage(lastLoadedPage: Int) =
        localDataSource.updateLastLoadedPage(lastLoadedPage)

    override suspend fun getLastDelete() =
        localDataSource.getLastDeleteDB()

    override suspend fun updateLastDelete(lastDelete: Long) =
        localDataSource.updateLastDeleteDB(lastDelete)

    override suspend fun getTotalPages(): Int =
        localDataSource.getTotalPages()

    override suspend fun updateTotalPages(totalPages: Int) =
        localDataSource.updateTotalPages(totalPages)
}