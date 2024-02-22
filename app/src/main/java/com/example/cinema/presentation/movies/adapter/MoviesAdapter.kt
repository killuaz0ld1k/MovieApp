package com.example.cinema.presentation.movies.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.cinema.R
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.model.Movie

class MoviesAdapter(private val onClickItem : (movieId : Int) -> Unit) : ListAdapter<Movie,MoviesViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        )
    }
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onClickItem)
    }
}

class MoviesViewHolder(itemView : View) : ViewHolder(itemView) {

    private val pgAge : TextView = itemView.findViewById(R.id.pg_text)
    private val movieImage : ImageView = itemView.findViewById(R.id.movie_image)
    private val genre : TextView = itemView.findViewById(R.id. film_genre_text)
    private val reviewCount : TextView = itemView.findViewById(R.id.movie_reviews_count_text)
    private val starsImages: List<ImageView> = listOf(
        itemView.findViewById(R.id.star1_image),
        itemView.findViewById(R.id.star2_image),
        itemView.findViewById(R.id.star3_image),
        itemView.findViewById(R.id.star4_image),
        itemView.findViewById(R.id.star5_image)
    )
    private val movieLike : ImageView = itemView.findViewById(R.id.movie_like_image)
    private val filmName : TextView = itemView.findViewById(R.id.film_name_text)
    private val runningTime : TextView = itemView.findViewById(R.id.film_time_text)

    fun bind(item : Movie, onClickItem : (movieId : Int) -> Unit) {

        movieImage.load(item.imageUrl)

        starsImages.forEachIndexed { index, imageView ->
            val colorId = if (item.rating > index) R.color.pink_light else R.color.gray_dark
            ImageViewCompat.setImageTintList(
                imageView, ColorStateList.valueOf(
                    ContextCompat.getColor(imageView.context, colorId)
                )
            )
        }

        val context = itemView.context

        var isLiked = item.isLiked
        updateLikeColor(isLiked)

        movieLike.setOnClickListener {
            isLiked = !isLiked
            updateLikeColor(isLiked)
        }

        pgAge.text = context.getString(R.string.movies_list_allowed_age_template,item.pgAge)
        genre.text = item.genres.joinToString { it.name }
        reviewCount.text =
            context.getString(R.string.movies_list_reviews_template, item.reviewCount)
        filmName.text = item.title
        runningTime.text = item.runningTime.toString()

        itemView.setOnClickListener {
            onClickItem(item.id)
        }
    }
    private fun updateLikeColor(isLiked : Boolean) {
        if (isLiked) {
            movieLike.setColorFilter(ContextCompat.getColor(itemView.context,R.color.pink_light))
        }
        else movieLike.setColorFilter(ContextCompat.getColor(itemView.context,R.color.white))
    }

}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}