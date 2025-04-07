package ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.crearrepositorio.databinding.ItemActorBinding
import domain.ActorModel

class ActorAdapter (private var actors: List<ActorModel>) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    class ActorViewHolder(val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actors[position]
        with(holder.binding){
            titleTextView.text = actor.name
            imageActor.load(actor.image) {
                transformations(CircleCropTransformation())
            }
        }
    }

    fun updateActors(newActors: List<ActorModel>) {
        this.actors = newActors
    }

    override fun getItemCount() = actors.size
}