package com.example.crearrepositorio.features.actors.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.crearrepositorio.databinding.ItemActorBinding
import com.example.crearrepositorio.databinding.LoadingPartialBinding
import com.example.crearrepositorio.features.actors.domain.ActorModel

class ActorAdapter(private val onActorClicked: (ActorModel) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val actors: MutableList<ActorModel?> = mutableListOf()
    private var isLoading: Boolean = false

    companion object {
        private const val VIEW_TYPE_ACTOR = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    class ActorViewHolder(private val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(actor: ActorModel, onActorClicked: (ActorModel) -> Unit){
            with(binding){
                titleTextView.text = actor.name
                imageActor.load(actor.image) {
                    transformations(CircleCropTransformation())
                }
                root.setOnClickListener {
                    onActorClicked(actor)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == actors.size) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ACTOR
        }
    }

    class LoadingViewHolder(binding: LoadingPartialBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ACTOR -> ActorViewHolder(ItemActorBinding.inflate(inflater, parent, false))
            VIEW_TYPE_LOADING -> LoadingViewHolder(LoadingPartialBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ActorViewHolder -> actors[position]?.let { holder.bind(it, onActorClicked) }
            is LoadingViewHolder -> { }
        }
    }

    fun updateActors(newActors: List<ActorModel>) {
        actors.clear()
        actors.addAll(newActors)
        notifyDataSetChanged()
    }

    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            notifyItemInserted(itemCount)
        }
    }

    fun hideLoading() {
        if (isLoading) {
            isLoading = false
            val lastIndex = itemCount
            if (lastIndex > 0) {
                notifyItemRemoved(lastIndex)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) {
            actors.size + 1
        } else {
            actors.size
        }
    }
}