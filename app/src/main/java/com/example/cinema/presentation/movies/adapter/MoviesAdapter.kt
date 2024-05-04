package com.example.cinema.presentation.movies.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.cinema.R
import com.example.cinema.databinding.ItemMovieBinding
import com.example.cinema.domain.model.Movie

class MoviesAdapter(private val onClickItem : (movieId : Int) -> Unit) : ListAdapter<Movie,MoviesViewHolder>(MoviesDiffUtil()) {

    private lateinit var binding : ItemMovieBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MoviesViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position),onClickItem)
    }

    fun clearMovies() {
        submitList(emptyList())
    }
}

class MoviesViewHolder(private val binding: ItemMovieBinding) : ViewHolder(binding.root) {

    private val starsImages: List<ImageView> = listOf(
        binding.star1Image,
        binding.star2Image,
        binding.star3Image,
        binding.star4Image,
        binding.star5Image
    )
    private val movieLike : ImageView = binding.movieLikeImage

    fun bind(item : Movie, onClickItem : (movieId : Int) -> Unit) {

        val context = itemView.context

        with(binding) {
            movieImage.load(item.imageUrl)
            pgText.text = context.getString(R.string.movies_list_allowed_age_template,item.pgAge)
            filmGenreText.text = item.genres.joinToString { it.name }
            movieReviewsCountText.text =
                context.getString(R.string.movies_list_reviews_template, item.reviewCount)
            filmNameText.text = item.title
            filmTimeText.text = item.runningTime.toString()
        }

        starsImages.forEachIndexed { index, imageView ->
            val colorId = if (item.rating / 2 > index) R.color.pink_light else R.color.gray_dark
            ImageViewCompat.setImageTintList(
                imageView, ColorStateList.valueOf(
                    ContextCompat.getColor(imageView.context, colorId)
                )
            )
        }

        var isLiked = item.isLiked
        updateLikeColor(isLiked)

        movieLike.setOnClickListener {
            isLiked = !isLiked
            updateLikeColor(isLiked)
        }

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