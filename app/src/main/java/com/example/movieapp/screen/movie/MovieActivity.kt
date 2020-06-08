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
import com.example.movieapp.utilities.BaseActivity
import kotlinx.android.synthetic.main.activity_genre.*
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MovieActivity : BaseActivity(), MovieContracts.view {

    var presenter: MoviePresenterImpl? = MoviePresenterImpl(this)
    var genreId = 0
    var genreName = ""
    private var page by Delegates.notNull<Int>()
    private var totalPage by Delegates.notNull<Int>()
    private var isLoading by Delegates.notNull<Boolean>()

    var resultData: ArrayList<ResultsItem?> = ArrayList()
    var passingData: List<ResultsItem?> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        genreId = intent.getIntExtra("genreId", 1)
        genreName = intent.getStringExtra("genreName")
        page = 1
        totalPage = 0
        initialLoad()
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

    fun initialLoad() {
        showLoading(true)
        coroutineScope.launch(Dispatchers.Main) {
            if(isInternetAvailable()) {
                presenter?.loadMovie(genreId, page)
            } else {
                showToast("Maaf Koneksi Tidak Tersedia")
            }
        }

    }

    override fun showData(data: List<ResultsItem?>) {
        hideLoading()
        totalPage = data.size
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
            if(resultData.isNullOrEmpty()) {
                for (i in 1..data.size -1) {
                    resultData.add(data[i])
                }
            } else {
                for (i in 1..resultData.size -1){
                    resultData.add(data[i])
                }
            }
            passingData = resultData.toList()
            val movieAdapter = MovieAdapter(passingData)
            rvMovie.apply {
                layoutManager = LinearLayoutManager(this@MovieActivity)
                adapter = movieAdapter
            }
            movieAdapter.notifyDataSetChanged()
        }

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

    override fun showToast(params:String) {
        Toast.makeText(this, params, Toast.LENGTH_LONG).show()
    }
}