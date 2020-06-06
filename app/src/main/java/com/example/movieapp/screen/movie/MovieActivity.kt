package com.example.movieapp.screen.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.GenresItem
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.screen.detail.DetailMovieActivity
import com.example.movieapp.screen.genre.GenreAdapter
import com.example.movieapp.screen.genre.GenreContracts
import com.example.movieapp.screen.genre.GenrePresenterImpl
import kotlinx.android.synthetic.main.activity_genre.*
import kotlinx.android.synthetic.main.activity_movie.*
import kotlin.properties.Delegates

class MovieActivity : AppCompatActivity(), MovieContracts.view {

    var presenter: MoviePresenterImpl? = MoviePresenterImpl(this)
    var genreId = 0
    var genreName = ""
    private var page by Delegates.notNull<Int>()
    private var totalPage by Delegates.notNull<Int>()
    private var isLoading by Delegates.notNull<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        genreId = intent.getIntExtra("genreId", 1)
        genreName = intent.getStringExtra("genreName")
        page = 1
        totalPage = 0
        initialLoad()
        initListener()
        rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager?.itemCount
                val lastVisiblePosition = linearLayoutManager?.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if (!isLoading && isLastPosition && page < 50) {
                    showLoading(true)
                    page = page.let { it.plus(1) }
                    initialLoad()
                }
            }
        })

    }

    fun initialLoad() {
        Log.e("message","page $page")

        showLoading(true)
        presenter?.loadMovie(genreId, page)
    }

    override fun showData(data: List<ResultsItem?>) {
        hideLoading()
        if(page == 1) {
            val movieAdapter = MovieAdapter(data)
            rvMovie.apply {
                layoutManager = LinearLayoutManager(this@MovieActivity)
                adapter = movieAdapter
            }

            movieAdapter.setItemClickListener(object:
                MovieAdapter.OnItemClickListener {
                override fun onClick(view: View, itemView: ResultsItem) {
                    openDetailMovie(
                        itemView.id,
                        itemView.name
                    )
                }
            })
            movieAdapter.notifyDataSetChanged()
        } else {
            Log.e("message","dataNew $data")
            val movieAdapter = MovieAdapter(data)
            movieAdapter.refreshAdapter(data)
        }



    }

    private fun initListener() {
        rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager?.itemCount
                val lastVisiblePosition = linearLayoutManager?.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if (!isLoading && isLastPosition && page < totalPage) {
                    showLoading(true)
                    page = page.let { it.plus(1) }
                    initialLoad()
                }
            }
        })
    }

    private fun showLoading(isRefresh: Boolean) {
        isLoading = true
        progressbar.visibility = View.VISIBLE
        rvMovie.visibility.let {
            if (isRefresh) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun hideLoading() {
        isLoading = false
        progressbar.visibility = View.GONE
        rvMovie.visibility = View.VISIBLE
    }

    fun openDetailMovie(id:Int?, name:String?) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("movieId",id)
        intent.putExtra("movieName", name)
        intent.putExtra("genreMovie", genreName)
        startActivity(intent)
    }

    override fun showToast() {
        Toast.makeText(this, "Maaf Data Tidak Tersedia", Toast.LENGTH_LONG).show()
    }
}