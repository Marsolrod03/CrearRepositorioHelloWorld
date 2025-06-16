package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.MovieModel
import com.example.movies.ui.databinding.LoadMoreMoviePagesBinding
import com.example.movies.ui.databinding.ViewMovieItemBinding

class MoviesAdapter(private val onMovieClick: (MovieModel) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listMovies: MutableList<MovieModel?> = mutableListOf()
    private var isLoading = false

    companion object {
        private const val VIEW_TYPE_MOVIE = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MOVIE -> MoviesViewHolder(
                ViewMovieItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            VIEW_TYPE_LOADING -> LoadingPartialViewHolder(
                LoadMoreMoviePagesBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when(holder){
            is MoviesViewHolder -> holder.bind(listMovies[position], onMovieClick)
            is LoadingPartialViewHolder -> Unit
        }
    }

    override fun getItemCount(): Int {
        return if(isLoading){
            listMovies.size + 1
        }else{
            listMovies.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listMovies.size) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_MOVIE
        }
    }

    fun updateMovies(movies: List<MovieModel>) {
        listMovies.clear()
        listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    fun manageLoadingPartial(show: Boolean) {
        if (show && !isLoading) {
            isLoading = true
            notifyItemInserted(itemCount)
        } else if (!show && isLoading) {
            isLoading = false
            val loadingIndex = itemCount
            if (loadingIndex > 0) {
                notifyItemRemoved(loadingIndex - 1)
            }
        }
    }

    class MoviesViewHolder(private val binding: ViewMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel?, onMovieClick: (MovieModel) -> Unit) {
            with(binding) {
                MovieTitle.text = movie?.title
                MovieOverview.text = movie?.overview
                ImageMovie.load(movie?.poster_path)
                root.setOnClickListener {
                    movie?.let { onMovieClick(it) }
                }
            }
        }
    }

    class LoadingPartialViewHolder(binding: LoadMoreMoviePagesBinding) :
        RecyclerView.ViewHolder(binding.root)
}