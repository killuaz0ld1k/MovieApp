package com.example.cinema.presentation.movies.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.repository.MovieRepository
import com.example.cinema.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MoviesListViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _state: MutableLiveData<Result<List<Movie>>> = MutableLiveData()
    val state: LiveData<Result<List<Movie>>> = _state

    var currentPage : Int = 1
    var isLoading = false

    @SuppressLint("SuspiciousIndentation")
    fun loadMoviesFromRepository() {
        if (isLoading) {
            return
        }
        _state.postValue(Result.Loading())
        isLoading = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.loadMovies(currentPage)
                    _state.postValue(Result.Success(response))
                    currentPage++
                } catch (e: Exception) {
                    _state.postValue(Result.Error(e.message))
                }
                finally {
                    isLoading = false
                }
            }
    }
}
