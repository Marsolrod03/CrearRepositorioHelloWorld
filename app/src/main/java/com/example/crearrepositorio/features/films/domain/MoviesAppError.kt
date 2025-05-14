package com.example.crearrepositorio.features.films.domain

sealed class MoviesAppError : Throwable() {
    data object Unauthorized : MoviesAppError() //401
    data object Forbidden : MoviesAppError()  //403
    data object BadRequest : MoviesAppError() //400
    data object NotFound : MoviesAppError() //404
    data object ServerError : MoviesAppError() //500 - 599
    data object NoInternet : MoviesAppError()
    data class UnknownError(val messageError: String? = null) : MoviesAppError()
}