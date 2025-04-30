package com.example.crearrepositorio.features.series.domain

data class SeriesWrapper(
    val hashMorePages: Boolean, val listSeries: List<SerieModel>, val isFirstPage: Boolean
)
