package com.example.crearrepositorio.features.series.domain

import com.example.crearrepositorio.features.series.domain.model.SerieModel

data class SeriesWrapper(
    val hashMorePages: Boolean, val listSeries: List<SerieModel>
)
