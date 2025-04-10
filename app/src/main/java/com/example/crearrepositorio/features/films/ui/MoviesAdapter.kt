package com.example.crearrepositorio.features.films.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.databinding.ViewMovieItemBinding
import com.example.crearrepositorio.features.films.domain.MovieModel

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
            binding.MovieTitle.text = movie.title
            binding.GenresMovie.text = movie.genresName.joinToString(", ")
        }
    }
}