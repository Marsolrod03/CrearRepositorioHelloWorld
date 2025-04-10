package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.common.ServiceLocator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun readJsonActors(fileName: String): List<ActorDTO> {
    val gson = Gson()
    val jsonString = ServiceLocator.applicationContext.assets.open(fileName).bufferedReader().use { it.readText() }

    val jsonResponseType = object : TypeToken<PagedResultDTO>() {}.type
    val jsonList: PagedResultDTO = gson.fromJson(jsonString, jsonResponseType)

    return jsonList.results
}