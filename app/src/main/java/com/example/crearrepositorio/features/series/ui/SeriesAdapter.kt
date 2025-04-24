package com.example.crearrepositorio.features.series.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.crearrepositorio.databinding.VistaSerieBinding
import com.example.crearrepositorio.features.series.domain.SerieModel


class SeriesAdapter () :
    RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

        private val series: MutableList<SerieModel> = mutableListOf()

    class SeriesViewHolder(val binding: VistaSerieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(serie: SerieModel) {
            with(binding) {
                title.text = serie.name
                description.text = serie.overview
                imageView.load(serie.poster_path)
            }
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
            val binding = VistaSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SeriesViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
           holder.bind(series[position])
        }

        override fun getItemCount(): Int {
            return series.size
        }

        fun updateSeries(newSeries: List<SerieModel>) {
            series.clear()
            series.addAll(newSeries)
            notifyDataSetChanged()
        }


    }