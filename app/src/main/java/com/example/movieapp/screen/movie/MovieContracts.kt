package com.example.movieapp.screen.movie

import com.example.movieapp.model.GenresItem
import com.example.movieapp.model.ResultsItem

interface MovieContracts {

    interface presenter {
        fun loadMovie(genreID:String)
    }

    interface view {
        fun showData(data:List<ResultsItem?>)
        fun showToast(params:String)
    }
}