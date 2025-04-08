package com.example.crearrepositorio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.databinding.VistaSerieBinding
import com.example.crearrepositorio.domain.SerieModel


class SeriesAdapter () :
    RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

        private val series: MutableList<SerieModel> = mutableListOf()

    class SeriesViewHolder(val binding: VistaSerieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(serie: SerieModel) {
            with(binding) {
                title.text = serie.titulo
                genre.text = serie.genero
                seasons.text = serie.temporada
                description.text = serie.descripcion
                imageView.setImageResource(serie.imagen)
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