package com.example.crearrepositorio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.crearrepositorio.databinding.ItemActorBinding
import domain.ActorModel

class ActorAdapter (private val actors: List<ActorModel>) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    class ActorViewHolder(val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actors[position]
        holder.binding.titleTextView.text = actor.title
        Glide.with(holder.binding.imageActor.context)
            .load(actor.image)
            .transform(CircleCrop())
            .into(holder.binding.imageActor)
    }

    override fun getItemCount() = actors.size

}