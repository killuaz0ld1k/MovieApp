package com.example.cinema.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinema.R
import com.example.cinema.presentation.moviedetails.view.MovieDetailsFragment
import com.example.cinema.presentation.movies.view.MoviesFragment
import com.example.cinema.domain.model.Movie
import com.example.cinema.presentation.moviedetails.view.BackToListMovies
import com.example.cinema.presentation.movies.view.ClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ClickListener,BackToListMovies {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,MoviesFragment()).commit()
    }

    private fun routeToMovieSelected(movieId: Int) {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_view,MovieDetailsFragment.newInstance(movieId)).addToBackStack("MovieDetails").commit()
    }

    override fun onClickItem(movieId: Int) {
        routeToMovieSelected(movieId)
    }

    override fun clickBackButton() {
        routeBack()
    }

    private fun routeToMovieList() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,MoviesFragment()).commit()
    }

    private fun routeBack() {
        supportFragmentManager.popBackStack()
    }
}