package com.example.movieapp.screen.movie

import android.util.Log
import com.example.movieapp.model.GenreResponse
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.network.Retrofit
import com.example.movieapp.screen.genre.GenreContracts
import com.example.movieapp.utilities.AppConfig
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviePresenterImpl( var view: MovieContracts.view?) {
    lateinit var call: Single<MovieResponse>
    val compositeDisposable = CompositeDisposable()

    fun loadMovie(genreId:Int,page:Int) {
        val api_key = AppConfig.apiKey
        call = Retrofit().apiService.getAllMovie(genreId, api_key, page)
        val disposable = call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                view?.showData(data.results!!)
            }, { error ->
                Log.e("message","message $error")
                parseError()
            })
        compositeDisposable.add(disposable)
    }
    fun parseError() {
        view?.showToast("Maaf Data Tidak Tersedia")
    }

}