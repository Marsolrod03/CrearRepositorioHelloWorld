package com.example.crearrepositorio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.databinding.ViewMovieItemBinding
import com.example.crearrepositorio.domain.entities.MovieModel

class MoviesAdapter() :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

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
        movies.clear()
        movies.addAll(fakeMovies)
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