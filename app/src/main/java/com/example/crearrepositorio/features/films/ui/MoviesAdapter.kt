package com.example.crearrepositorio.features.films.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.crearrepositorio.databinding.ViewMovieItemBinding
import com.example.crearrepositorio.features.films.domain.MovieModel

class MoviesAdapter(): RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val movies: MutableList<MovieModel> = mutableListOf()

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
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    fun updateMovies(fakeMovies: List<MovieModel>) {
        movies.addAll(fakeMovies)
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