package com.example.domain

sealed class AppError(message: String? = null) : Throwable(message) {
    data object Unauthorized : AppError("Unauthorized") //401
    data object Forbidden : AppError("Forbidden")  //403
    data object BadRequest : AppError("Bad request") //400
    data object NotFound : AppError("Not found") //404
    data object ServerError : AppError("Server error") //500 - 599
    data object NoInternet : AppError("No internet connection")
    data class UnknownError(val messageError: String? = null) : AppError()
}
