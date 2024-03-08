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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinema.R
import com.example.cinema.presentation.movies.adapter.MoviesAdapter
import com.example.cinema.presentation.movies.viewModel.MoviesListViewModel
import com.example.cinema.presentation.movies.viewModel.State
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
        val swipeRefresh : SwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        val recycleMovies : RecyclerView = view.findViewById(R.id.recycler_movies)

        recycleMovies.apply {
            this.setHasFixedSize(true)
            this.layoutManager = GridLayoutManager(this.context,2)
            val adapter = MoviesAdapter {movieId ->
                listener?.onClickItem(movieId)
            }
            this.adapter = adapter
            loadDataToAdapter(adapter,view)
            swipeRefresh.setOnRefreshListener {
                loadDataToAdapter(adapter,view)
                swipeRefresh.isRefreshing = false
            }
        }
    }
    private fun loadDataToAdapter(adapter: MoviesAdapter, view : View) { // TODO() выяснить почему не обновляются данные после эрора

        val progressBar : ProgressBar = view.findViewById(R.id.progress_bar)
        val errorMessage : TextView = view.findViewById(R.id.error_message_textView)

        viewModel.state.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.isLoading -> {
                    errorMessage.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    adapter.submitList(state.movies)
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
                is State.Error -> {
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            }
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