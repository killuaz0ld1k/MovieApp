package com.example.cinema.presentation.movies.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.cinema.domain.model.Movie

class MoviesDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}