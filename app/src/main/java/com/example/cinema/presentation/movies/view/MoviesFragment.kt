package com.example.cinema.presentation.movies.view

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMoviesBinding
import com.example.cinema.presentation.movies.adapter.MoviesAdapter
import com.example.cinema.presentation.movies.viewModel.MoviesListViewModel
import com.example.cinema.domain.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var listener : ClickListener? = null
    private val viewModel: MoviesListViewModel by viewModels()

    private lateinit var binding : FragmentMoviesBinding
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefresh : SwipeRefreshLayout = binding.swipeRefreshLayout
        val recycleMovies : RecyclerView = binding.recyclerMovies

        recycleMovies.apply {

            val layoutManagerGrid = GridLayoutManager(this.context,2)
            moviesAdapter = MoviesAdapter {movieId ->
                listener?.onClickItem(movieId,view)
            }


            this.layoutManager = layoutManagerGrid
            this.adapter = moviesAdapter

            swipeRefresh.setOnRefreshListener {
                moviesAdapter.clearMovies()
                viewModel.currentPage = 1
                viewModel.loadMoviesFromRepository()
                swipeRefresh.isRefreshing = false
            }

            this.addOnScrollListener(object : PaginationScrollListener(layoutManagerGrid) {

                override fun loadMoreItems()  {
                    viewModel.loadMoviesFromRepository()
                }
                override fun isLoading(): Boolean = viewModel.isLoading
            })
            observeViewModel(moviesAdapter)
        }

    }
    private fun observeViewModel(adapter: MoviesAdapter) {

        val progressBar : ProgressBar = binding.progressCircular
        val errorMessage : TextView = binding.exceptionTextView

        viewModel.state.observe(viewLifecycleOwner) {state ->
            when (state) {
                is Result.Loading -> {
                    errorMessage.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    adapter.submitList(state.data)
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    errorMessage.text = state.message
                    errorMessage.visibility = View.VISIBLE
                }
            }
        }
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