package com.example.cinema.presentation.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MoviesListViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

//    private val _moviesList : MutableLiveData<List<Movie>> = MutableLiveData()
//    val moviesList : LiveData<List<Movie>> = _moviesList

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State> = _state

    init {
        _state.postValue(State.isLoading())
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(State.isLoading())
                _state.postValue(State.Success(repository.loadMovies()))
            } catch (e: Exception) {
                _state.postValue(State.Error())
            }
        }
    }
}

sealed class State {
    class isLoading : State()
    class Error : State()
    data class Success(val movies : List<Movie>) : State()
}