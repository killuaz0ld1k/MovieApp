package com.example.cinema.presentation.moviedetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.cinema.R
import com.example.cinema.domain.model.Actor

class MovieDetailsAdapter() : ListAdapter<Actor,MovieDetailsViewHolder>(ActorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsViewHolder {
        return MovieDetailsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_actor,parent,false))
    }

    override fun onBindViewHolder(holder: MovieDetailsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class MovieDetailsViewHolder(itemView : View) : ViewHolder(itemView) {

    private var actorName : TextView = itemView.findViewById(R.id.actor_name)
    private var actorImage : ImageView = itemView.findViewById(R.id.actor_image)

    fun bind(item : Actor) {
        actorName.text = item.name
        actorImage.load(item.imageUrl)
    }

}

class ActorDiffCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.actorId == newItem.actorId
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}
