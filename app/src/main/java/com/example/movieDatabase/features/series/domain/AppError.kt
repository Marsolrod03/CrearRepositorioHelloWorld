package com.example.movieDatabase.features.series.domain

sealed class AppError : Throwable() {
    data object Unauthorized : AppError() //401
    data object Forbidden : AppError()  //403
    data object BadRequest : AppError() //400
    data object NotFound : AppError() //404
    data object ServerError : AppError() //500 - 599
    data object NoInternet : AppError()
    data class UnknownError(val messageError: String? = null) : AppError()
}