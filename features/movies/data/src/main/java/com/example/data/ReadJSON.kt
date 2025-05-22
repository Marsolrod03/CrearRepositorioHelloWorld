package com.example.data

import com.example.lib.common.ServiceLocator
import com.example.data.dto.MovieDTO
import com.example.data.dto.MoviePageDTO
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