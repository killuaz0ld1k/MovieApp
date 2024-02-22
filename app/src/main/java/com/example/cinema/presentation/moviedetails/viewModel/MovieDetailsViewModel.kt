package com.example.cinema.presentation.moviedetails.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.model.MovieDetails
import com.example.cinema.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val movieDetailsList = MutableLiveData<MovieDetails>()

    fun loadMovieDetails(movieId : Int) {
        viewModelScope.launch {
            movieDetailsList.postValue(movieRepository.loadMovie(movieId))
        }
    }

}