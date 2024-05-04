package com.example.cinema.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.cinema.R
import com.example.cinema.presentation.moviedetails.view.BackToListMovies
import com.example.cinema.presentation.moviedetails.view.MovieDetailsFragment
import com.example.cinema.presentation.moviedetails.view.MovieDetailsFragmentDirections
import com.example.cinema.presentation.movies.view.ClickListener
import com.example.cinema.presentation.movies.view.MoviesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ClickListener,BackToListMovies {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onClickItem(movieId: Int,view: View) {
        view.findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movieId))
    }
    override fun clickBackButton(view: View) {
        navController.popBackStack(R.id.movieDetailsFragment,true)
    }
}