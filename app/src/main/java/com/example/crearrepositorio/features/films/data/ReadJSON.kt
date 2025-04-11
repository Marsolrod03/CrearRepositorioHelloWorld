package com.example.crearrepositorio.features.films.data

import com.example.crearrepositorio.common.ServiceLocator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun readJson(fileName: String): List<MovieDTO> {
    val gson = Gson()
    val jsonString = ServiceLocator.applicationContext.assets.open(fileName).bufferedReader()
        .use { it.readText() }

    val jsonResponseType = object : TypeToken<MoviePageDTO>() {}.type
    val jsonList: MoviePageDTO = gson.fromJson(jsonString, jsonResponseType)

    return jsonList.results
}