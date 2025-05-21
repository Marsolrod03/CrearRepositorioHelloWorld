package com.example.crearrepositorio.features.films.data.implementation

import com.example.crearrepositorio.features.films.data.dataSource.MoviesLocalDataSource
import com.example.crearrepositorio.features.films.data.dataSource.MoviesNetworkDataSource
import com.example.crearrepositorio.features.films.data.mapper.toMovieEntity
import com.example.crearrepositorio.features.films.data.mapper.toMovieModel
import com.example.crearrepositorio.features.films.domain.MovieWrapper
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
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
            var lastMovieInDatabase = getLastMoviePage()
            val databaseMovies = getAllMoviesFromDatabase()

            if (lastMovieInDatabase > currentPage) {
                currentPage = lastMovieInDatabase
                emit(Result.success(MovieWrapper(true, databaseMovies)))
            } else {
                getAllMoviesFromApi(currentPage)
                    .collect { result ->
                        result.onSuccess { movieWrapper ->
                            insertAllMovies(movieWrapper.movieList)
                            currentPage++
                            if (currentPage > lastMovieInDatabase) {
                                updateLastLoadedPage(currentPage)
                            }
                            emit(Result.success(movieWrapper))
                        }
                        result.onFailure {
                            emit(Result.success(MovieWrapper(false, databaseMovies)))
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

    override suspend fun clearDatabase(timeRN: Long) {
        clearMovieDatabase()
        deleteAllMoviePages()
        updateLastDelete(timeRN)
    }

    override fun getAllMoviesFromApi(page: Int): Flow<Result<MovieWrapper>> = flow {
        try {
            val pagedResult = networkDataSource.fetchPopularMovies(currentPage)
            pagedResult?.let {
                val movieList = it.results.map { movie -> movie.toMovieModel() }
                accumulatedMovies.addAll(movieList)
                val hasMorePages = it.page < it.total_pages
                val movieWrapper = MovieWrapper(hasMorePages, accumulatedMovies)
                currentPage++
                emit(Result.success(movieWrapper))
            } ?: run {
                emit(Result.success(MovieWrapper(false, emptyList())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    override suspend fun getAllMoviesFromDatabase(): List<MovieModel> =
        localDataSource.getAllMovies().map { it.toMovieModel() }


    override suspend fun insertAllMovies(movies: List<MovieModel>) =
        localDataSource.insertAllMovies(movies.map { it.toMovieEntity() })


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

}