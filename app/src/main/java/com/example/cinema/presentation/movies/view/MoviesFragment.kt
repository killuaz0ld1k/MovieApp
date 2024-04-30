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
import com.example.cinema.presentation.movies.adapter.MoviesAdapter
import com.example.cinema.presentation.movies.viewModel.MoviesListViewModel
import com.example.cinema.domain.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var listener : ClickListener? = null
    private val viewModel: MoviesListViewModel by viewModels()
    private lateinit var layoutManagerGrid : GridLayoutManager
    private lateinit var recycleMovies : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        // val swipeRefresh : SwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        recycleMovies = view.findViewById(R.id.recycler_movies)

        recycleMovies.apply {

            layoutManagerGrid = GridLayoutManager(this.context,2)
            val adapter = MoviesAdapter {movieId ->
                listener?.onClickItem(movieId,view)
            }


            this.layoutManager = layoutManagerGrid
            this.adapter = adapter

//            swipeRefresh.setOnRefreshListener {
//                viewModel.currentPage = 1
//                adapter.clearMovies()
//                viewModel.loadMoviesFromRepository()
//                swipeRefresh.isRefreshing = false
//            }

            this.addOnScrollListener(object : PaginationScrollListener(layoutManagerGrid) {

                override fun loadMoreItems()  {
                    viewModel.currentPage += 1
                    Log.d("MoviesListViewModel", "Current page before loading: $viewModel.currentPage")
                    viewModel.loadMoviesFromRepository()
                }
                override fun isLoading(): Boolean = viewModel.isLoading
            })
            observeViewModel(adapter,view)
        }

    }
    private fun observeViewModel(adapter: MoviesAdapter, view : View) {

//        val progressBar : ProgressBar = view.findViewById(R.id.progress_bar)
//        val errorMessage : TextView = view.findViewById(R.id.error_message_textView)

        viewModel.state.observe(viewLifecycleOwner) {state ->
            when (state) {
                is Result.Loading -> {
//                    errorMessage.visibility = View.GONE
//                    progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    adapter.saveData(state.data.orEmpty())

//                    progressBar.visibility = View.GONE
//                    errorMessage.visibility = View.GONE
                }
                is Result.Error -> {
//                    progressBar.visibility = View.GONE
//                    errorMessage.text = state.message
//                    errorMessage.visibility = View.VISIBLE
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

//    override fun onPause() {
//        super.onPause()
//        val firstVisibleItemPosition = layoutManagerGrid.findFirstVisibleItemPosition()
//        val spanCount = layoutManagerGrid.spanCount
//        viewModel.recyclerViewScrollPositionRow = firstVisibleItemPosition / spanCount
//        viewModel.recyclerViewScrollPositionColumn = firstVisibleItemPosition % spanCount
//    }
//    override fun onResume() {
//        super.onResume()
//        val spanCount = layoutManagerGrid.spanCount
//        val position = viewModel.recyclerViewScrollPositionRow * spanCount +
//                viewModel.recyclerViewScrollPositionColumn
//        layoutManagerGrid.scrollToPosition(position)
//    }
}