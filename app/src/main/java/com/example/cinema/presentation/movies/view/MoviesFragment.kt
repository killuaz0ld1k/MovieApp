package com.example.cinema.presentation.movies.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinema.R
import com.example.cinema.presentation.movies.adapter.MoviesAdapter
import com.example.cinema.presentation.movies.viewModel.MoviesListViewModel
import com.example.cinema.domain.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        val swipeRefresh : SwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        val recycleMovies : RecyclerView = view.findViewById(R.id.recycler_movies)

        recycleMovies.apply {

            val layoutManager = GridLayoutManager(this.context,2)
            val adapter = MoviesAdapter {movieId ->
                listener?.onClickItem(movieId)
            }

            this.layoutManager = layoutManager
            this.adapter = adapter

            observeViewModel(adapter,view)

            swipeRefresh.setOnRefreshListener {
                viewModel.currentPage = 1
                adapter.clearMovies()
                loadMovies()
                swipeRefresh.isRefreshing = false
            }
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!viewModel.isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        viewModel.loadMoviesFromRepository()
                    }
                }
            })
        }
        loadMovies()
    }
    private fun observeViewModel(adapter: MoviesAdapter, view : View) { // TODO() выяснить почему не обновляются данные после эрора

        val progressBar : ProgressBar = view.findViewById(R.id.progress_bar)
        val errorMessage : TextView = view.findViewById(R.id.error_message_textView)

        viewModel.state.observe(viewLifecycleOwner) {state ->
            when (state) {
                is Result.Loading -> {
                    errorMessage.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    adapter.addDataToAdapter(state.data.orEmpty())
                    adapter.notifyDataSetChanged()

                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            viewModel.loadMoviesFromRepository()
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