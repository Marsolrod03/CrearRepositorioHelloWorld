package com.example.crearrepositorio.features.films.data

import com.example.crearrepositorio.features.films.domain.MovieModel
import com.example.crearrepositorio.features.films.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl : MovieRepository {

    override fun createMovies(): Flow<List<MovieModel>> = flow{
        val jsonName = "movies.json"
        val moviesDTO = readJson(jsonName)

        emit(moviesDTO.map {
            it.toMovieModel()
        })
    }

    }
