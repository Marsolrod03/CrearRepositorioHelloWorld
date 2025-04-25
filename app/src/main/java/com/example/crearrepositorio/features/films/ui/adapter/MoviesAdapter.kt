package com.example.crearrepositorio.features.films.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.crearrepositorio.databinding.ViewMovieItemBinding
import com.example.crearrepositorio.features.films.domain.model.MovieModel

class MoviesAdapter(): RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val listMovies: MutableList<MovieModel> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val binding = ViewMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MoviesViewHolder,
        position: Int
    ) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount() = listMovies.size

    fun updateMovies(movies: List<MovieModel>) {
        listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    class MoviesViewHolder(private val binding: ViewMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel) {
            with(binding){
                MovieTitle.text = movie.title
                MovieOverview.text = movie.overview
                ImageMovie.load(movie.poster_path)
            }
        }
    }
}