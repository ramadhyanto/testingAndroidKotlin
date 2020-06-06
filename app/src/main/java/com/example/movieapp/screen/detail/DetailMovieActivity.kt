package com.example.movieapp.screen.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.model.DetailMovieResponse
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.model.ReviewResponse
import com.example.movieapp.screen.genre.GenreAdapter
import com.example.movieapp.screen.movie.MovieAdapter
import com.example.movieapp.screen.movie.MovieContracts
import com.example.movieapp.screen.movie.MoviePresenterImpl
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.activity_genre.*
import kotlinx.android.synthetic.main.activity_movie.*

class DetailMovieActivity : AppCompatActivity(), DetailMovieContracts.view {
    var presenter: DetailMoviePresenterImpl? = DetailMoviePresenterImpl(this)
    var movieId = 0
    var movieName = ""
    var genreMovie = ""
    var playVideo = ""
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        movieId = intent.getIntExtra("movieId", 1)
        movieName = intent.getStringExtra("movieName")
        genreMovie = intent.getStringExtra("genreMovie")
        webView = findViewById(R.id.webview)
        webView.getSettings().setJavaScriptEnabled(true);
        playVideo= "<html><body>Youtube video .. <br> <iframe class=\"youtube-player\" type=\"text/html\" width=\"640\" height=\"385\" src=\"http://www.youtube.com/embed/TcMBFSGVi1c\" frameborder=\"0\"></body></html>"
        webView.loadData(playVideo, "text/html", "utf-8");
        initialLoad()

    }

    fun initialLoad() {
        presenter?.loadMovie(movieId)
        presenter?.loadReview(movieId)
    }

    override fun showData(data: DetailMovieResponse?) {
            tv_title_movie.setText(data!!.title)
            tv_genre_movie.setText(genreMovie)
            tv_status_movie.setText(data!!.status)
            tv_release_date_movie.setText(data!!.releaseDate)
            tv_description_movie.setText(data!!.overview)
    }

    override fun showReview(data: ReviewResponse) {
        if (data.results.isNullOrEmpty()) {
            tv_noreview.visibility = View.VISIBLE
            Toast.makeText(this, "Maaf Review Tidak Tersedia", Toast.LENGTH_LONG).show()
        } else {
            val reviewAdapter = DetailMovieAdapter(data.results)
            rvGenre.apply {
                layoutManager = LinearLayoutManager(this@DetailMovieActivity)
                adapter = reviewAdapter
            }
        }
    }

    override fun showToast() {
        Toast.makeText(this, "Maaf Data Tidak Tersedia", Toast.LENGTH_LONG).show()
    }
}