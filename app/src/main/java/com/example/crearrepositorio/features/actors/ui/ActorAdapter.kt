package com.example.crearrepositorio.features.actors.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.crearrepositorio.databinding.ItemActorBinding
import com.example.crearrepositorio.features.actors.domain.ActorModel

class ActorAdapter:
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private val actors: MutableList<ActorModel> = mutableListOf()

    class ActorViewHolder(private val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(actor: ActorModel){
            with(binding){
                titleTextView.text = actor.name
                imageActor.load(actor.image) {
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    fun updateActors(newActors: List<ActorModel>) {
        actors.clear()
        actors.addAll(newActors)
        notifyDataSetChanged()
    }

    override fun getItemCount() = actors.size
}