package com.example.data


import android.content.Context
import com.example.data.dto.MovieDTO
import com.example.data.dto.MoviePageDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JsonReader @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {
    fun readJson(fileName: String): List<MovieDTO> {

    val gson = Gson()
    val jsonString = applicationContext.assets.open(fileName).bufferedReader()
        .use { it.readText() }

    val jsonResponseType = object : TypeToken<MoviePageDTO>() {}.type
    val jsonList: MoviePageDTO = gson.fromJson(jsonString, jsonResponseType)

    return jsonList.results
    }
}