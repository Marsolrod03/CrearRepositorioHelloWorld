package com.example.crearrepositorio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.databinding.VistaSerieBinding
import com.example.crearrepositorio.domain.SerieModel


class SeriesAdapter (private var seriesList: List<SerieModel>) :
    RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
            val binding = VistaSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SeriesViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
            val item = seriesList[position]
            with(holder.binding) {
                title.text = item.titulo
                genre.text = item.genero
                seasons.text = item.temporada
                description.text = item.descripcion
                imageView.setImageResource(item.imagen)
            }
        }

        override fun getItemCount(): Int {
            return seriesList.size
        }

        fun updateSeries(series: List<SerieModel>) {
            this.seriesList = series
        }

        class SeriesViewHolder(val binding: VistaSerieBinding) :
            RecyclerView.ViewHolder(binding.root)
    }