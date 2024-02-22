package com.example.cinema.presentation.movies.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.presentation.movies.adapter.MoviesAdapter
import com.example.cinema.presentation.movies.viewModel.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var listener : ClickListener? = null
    private val viewModel: MoviesListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycleMovies : RecyclerView = view.findViewById(R.id.recycler_movies)
        recycleMovies.apply {
            this.layoutManager = GridLayoutManager(this.context,2)
            val adapter = MoviesAdapter {movieId ->
                listener?.onClickItem(movieId)
            }
            this.adapter = adapter
            loadDataToAdapter(adapter)
        }
    }
    private fun loadDataToAdapter(adapter: MoviesAdapter) {
        viewModel.moviesList.observe(viewLifecycleOwner) {movies ->
            adapter.submitList(movies)
        }
        viewModel.loadMovies()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}