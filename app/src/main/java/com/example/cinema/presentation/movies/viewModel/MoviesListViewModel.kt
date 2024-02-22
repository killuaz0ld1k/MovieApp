package com.example.cinema.presentation.movies.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MoviesListViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {


    val moviesList = MutableLiveData<List<Movie>>()

    fun loadMovies() {
        viewModelScope.launch {
            moviesList.postValue(repository.loadMovies())
        }
    }
}