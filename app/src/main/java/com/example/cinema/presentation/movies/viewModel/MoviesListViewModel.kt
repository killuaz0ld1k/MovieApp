package com.example.cinema.presentation.movies.viewModel

import android.annotation.SuppressLint
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

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State> = _state

    var currentPage = 1

    var isLoading = false

    init {
        _state.postValue(State.isLoading())
        loadMovies()
    }

    @SuppressLint("SuspiciousIndentation")
    fun loadMovies() {
        if (isLoading) {
            return
        }
        isLoading = true
            viewModelScope.launch {
                try {
                    _state.postValue(State.isLoading())
                    _state.postValue(State.Success(repository.loadMovies(currentPage)))
                    currentPage++
                    isLoading = false
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