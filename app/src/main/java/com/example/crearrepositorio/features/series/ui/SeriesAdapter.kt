package com.example.crearrepositorio.features.series.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.crearrepositorio.databinding.FragmentLoadingBinding
import com.example.crearrepositorio.databinding.VistaSerieBinding
import com.example.crearrepositorio.features.series.domain.SerieModel


class SeriesAdapter(private val onSeriesClicked: (SerieModel) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val series: MutableList<SerieModel?> = mutableListOf()
    private var isLoading = false

    companion object {
        private const val VIEW_TYPE_SERIE = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    class SeriesViewHolder(private val binding: VistaSerieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(serie: SerieModel, onSeriesClicked: (SerieModel) -> Unit) {
            with(binding) {
                title.text = serie.name
                description.text = serie.overview
                imageView.load(serie.poster_path)
                itemView.setOnClickListener {
                    onSeriesClicked(serie)
                }
            }
        }
    }

    fun updateSeries(newSeries: List<SerieModel>) {
        series.clear()
        series.addAll(newSeries)
        notifyDataSetChanged()
    }

    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            notifyItemInserted(series.size)
        }else{
            isLoading = false
            val lastIndex = series.size
            if (lastIndex > 0){
                notifyItemRemoved(lastIndex)
            }
        }
    }



    override fun getItemCount(): Int{
        return if (isLoading) {
            series.size + 1
        } else {
            series.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == series.size) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_SERIE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SERIE -> SeriesViewHolder(VistaSerieBinding.inflate(inflater, parent, false))
            VIEW_TYPE_LOADING -> LoadingViewHolder(FragmentLoadingBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SeriesViewHolder -> series[position]?.let { holder.bind(it, onSeriesClicked) }
            is LoadingViewHolder -> {}
        }
    }


    class LoadingViewHolder(binding: FragmentLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}
