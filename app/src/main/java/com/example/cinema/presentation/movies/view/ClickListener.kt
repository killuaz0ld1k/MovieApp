package com.example.cinema.presentation.movies.view

import android.view.View

interface ClickListener {
    fun onClickItem(movieId : Int, view: View)
}