package com.example.crearrepositorio.features.films.data.mapper

import com.example.crearrepositorio.features.films.data.database.entities.MovieEntity
import com.example.crearrepositorio.features.films.data.dto.MovieDTO
import com.example.crearrepositorio.features.films.domain.model.MovieModel

fun MovieDTO.toMovieModel(): MovieModel{
    val basePath = "https://image.tmdb.org/t/p/w500/"
    val movieModel = MovieModel(
        id = id,
        overview = overview,
        poster_path = "$basePath$poster_path",
        title = title,
        backdrop_path = "$basePath$backdrop_path",
        popularity = popularity,
        release_date = release_date,
        vote_average = vote_average,
    )
    return movieModel
}

fun MovieEntity.toMovieModel(): MovieModel{
    val movieModel = MovieModel(
        id = id,
        overview = overview,
        poster_path = poster_path,
        title = title,
        backdrop_path = backdrop_path,
        popularity = popularity,
        release_date = release_date,
        vote_average = vote_average
    )
    return movieModel
}

fun MovieModel.toMovieEntity(): MovieEntity{
    val movieEntity = MovieEntity(
        id = id,
        overview = overview,
        poster_path = poster_path,
        title = title,
        backdrop_path = backdrop_path,
        popularity = popularity,
        release_date = release_date,
        vote_average = vote_average
    )
    return movieEntity
}