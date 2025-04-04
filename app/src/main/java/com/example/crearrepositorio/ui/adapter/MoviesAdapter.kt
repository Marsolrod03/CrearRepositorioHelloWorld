package com.example.crearrepositorio.ui.adapter

 import android.view.LayoutInflater
 import android.view.ViewGroup
 import androidx.recyclerview.widget.RecyclerView
 import com.example.crearrepositorio.databinding.ViewMovieItemBinding
 import com.example.crearrepositorio.domain.MovieModel

class MoviesAdapter(private var movies: List<MovieModel>):
      RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder >(){

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

    fun updateMovies(newMovies: List<MovieModel>) {
        this.movies = newMovies
    }

    class MoviesViewHolder(private val binding: ViewMovieItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieModel){
            binding.movieTitle.text = movie.title
        }
    }
}