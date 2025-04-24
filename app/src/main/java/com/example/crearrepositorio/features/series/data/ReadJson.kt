package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.common.ServiceLocator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun readJson(fileName: String): List<SeriesDTO> {
    val gson = Gson()
    val jsonString = ServiceLocator.applicationContext.assets.open(fileName).bufferedReader()
        .use { it.readText() }

    val seriesType = object : TypeToken<ResultsDTO>() {}.type
    val jsonList: ResultsDTO = gson.fromJson(jsonString,seriesType)

    return jsonList.results
}