package com.example.cinema.presentation.moviedetails.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import coil.load
import com.example.cinema.R
import com.example.cinema.data.work_manager.LoadMovieDetailsWorker
import com.example.cinema.domain.model.MovieDetails
import com.example.cinema.presentation.moviedetails.adapter.MovieDetailsAdapter
import com.example.cinema.presentation.moviedetails.viewModel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val detailViewModel : MovieDetailsViewModel by viewModels()
    private var toBackListener : BackToListMovies? = null
    private val args: MovieDetailsFragmentArgs by navArgs()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BackToListMovies) {
            toBackListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId

        view.findViewById<RecyclerView>(R.id.recycler_movies).apply {
            this.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            this.adapter = MovieDetailsAdapter()
        }

        detailViewModel.loadMovieDetails(movieId)

        detailViewModel.movieDetailsList.observe(viewLifecycleOwner) {
            loadDataToFragment(it)
            loadDataToAdapter(view, it)
        }

        view.findViewById<View>(R.id.back_button_layout).setOnClickListener() {
            toBackListener?.clickBackButton(it)
        }
        // startWork(movieId)

    }

    override fun onDetach() {
        toBackListener = null
        super.onDetach()
    }

    private fun loadDataToAdapter(view : View, movieDetails : MovieDetails) {

        val adapter = view.findViewById<RecyclerView>(R.id.recycler_movies).adapter as MovieDetailsAdapter
        adapter.submitList(movieDetails.actors)
    }
    private fun loadDataToFragment(movieDetails : MovieDetails) {

        view?.findViewById<ImageView>(R.id.movie_logo_image)?.load(movieDetails.detailImageUrl)

        view?.findViewById<TextView>(R.id.movie_age_restrictions_text)?.text =
            context?.getString(R.string.movies_list_allowed_age_template, movieDetails.pgAge)

        view?.findViewById<TextView>(R.id.movie_name_text)?.text = movieDetails.title
        view?.findViewById<TextView>(R.id.movie_tags_text)?.text = movieDetails.genres.joinToString { it.name }
        view?.findViewById<TextView>(R.id.movie_reviews_count_text)?.text =
            context?.getString(R.string.movies_list_reviews_template, movieDetails.reviewCount)
        view?.findViewById<TextView>(R.id.movie_storyline_text)?.text = movieDetails.storyLine
        val starsImages = listOf<ImageView?>(
            view?.findViewById(R.id.star1_image),
            view?.findViewById(R.id.star2_image),
            view?.findViewById(R.id.star3_image),
            view?.findViewById(R.id.star4_image),
            view?.findViewById(R.id.star5_image),
            view?.findViewById(R.id.star6_image),
            view?.findViewById(R.id.star7_image),
            view?.findViewById(R.id.star8_image),
            view?.findViewById(R.id.star9_image),
            view?.findViewById(R.id.star10_image)
        )
        starsImages.forEachIndexed { index, imageView ->
            val colorId = if (movieDetails.rating > index) R.color.pink_light else R.color.gray_dark
            ImageViewCompat.setImageTintList(
                imageView!!, ColorStateList.valueOf(
                    ContextCompat.getColor(imageView.context, colorId)
                )
            )
        }
    }
    private fun startWork(movieId: Int) {

        val movieData = Data.Builder().putInt("id",movieId).build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(LoadMovieDetailsWorker::class.java,8,TimeUnit.HOURS)
            .setConstraints(constraints)
            .setInputData(movieData)
            .setInitialDelay(4,TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this.requireContext()).enqueue(periodicWorkRequest)
    }
}