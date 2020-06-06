package com.example.movieapp.screen.detail

import android.util.Log
import com.example.movieapp.model.DetailMovieResponse
import com.example.movieapp.model.ReviewResponse
import com.example.movieapp.network.Retrofit
import com.example.movieapp.utilities.AppConfig
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailMoviePresenterImpl(var view: DetailMovieContracts.view) {
    lateinit var callMovie: Single<DetailMovieResponse>
    lateinit var callReview: Single<ReviewResponse>
    val compositeDisposable = CompositeDisposable()

    fun loadMovie(movieId:Int) {
        val api_key = AppConfig.apiKey
        callMovie = Retrofit().apiService.getDetailMovie(movieId, api_key)
        val disposable = callMovie.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                view?.showData(data)
            }, { error ->
                Log.e("message","errorMovie $error")
                parseError()
            })
        compositeDisposable.add(disposable)
    }

    fun loadReview(movieId:Int) {
        val api_key = AppConfig.apiKey
        callReview = Retrofit().apiService.getReview(movieId, api_key)
        val disposable = callReview.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                view?.showReview(data)
            }, { error ->
                Log.e("message","errorReview $error")
                parseError()
            })
        compositeDisposable.add(disposable)
    }

    fun parseError() {
        view?.showToast()
    }

}