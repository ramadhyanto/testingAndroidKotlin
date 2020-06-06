package com.example.movieapp.screen.genre

import com.example.movieapp.model.GenreResponse
import com.example.movieapp.model.GenresItem

interface GenreContracts {

    interface presenter {
        fun loadGenre()
    }

    interface view {
        fun showData(data:List<GenresItem?>)
        fun showToast()
    }
}