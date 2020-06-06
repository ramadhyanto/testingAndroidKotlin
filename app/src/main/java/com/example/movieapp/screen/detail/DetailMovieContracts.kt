package com.example.movieapp.screen.detail

import com.example.movieapp.model.DetailMovieResponse
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.model.ReviewResponse
import io.reactivex.Single

class DetailMovieContracts {

    interface presenter {
        fun loadMovie(genreID:String)
    }

    interface view {
        fun showData(data: DetailMovieResponse?)
        fun showReview(data: ReviewResponse)
        fun showToast()
    }



}