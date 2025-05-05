package com.example.crearrepositorio.features.films.data.mapper

import com.example.crearrepositorio.features.films.data.dto.MovieDTO
import com.example.crearrepositorio.features.films.domain.model.MovieModel

fun MovieDTO.toMovieModel(): MovieModel{
    val movieModel = MovieModel(
        overview = overview,
        poster_path = "https://image.tmdb.org/t/p/w500/$poster_path",
        title = title
    )
    return movieModel
}