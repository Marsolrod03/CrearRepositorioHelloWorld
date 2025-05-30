package com.example.domain

import com.example.domain.model.SerieModel

data class SeriesWrapper(
    val hashMorePages: Boolean, val listSeries: List<SerieModel>, val totalPages: Int
)
