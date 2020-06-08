package com.example.movieapp.screen.genre

import android.util.Log
import com.example.movieapp.model.GenreResponse
import com.example.movieapp.network.Retrofit
import com.example.movieapp.utilities.AppConfig
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class GenrePresenterImpl (
    var view: GenreContracts.view?
) {

    lateinit var call: Single<GenreResponse>
    val compositeDisposable = CompositeDisposable()

     fun loadGenre() {
        val api_key = AppConfig.apiKey
        call = Retrofit().apiService.getAllGenreMovie(api_key)
        val disposable = call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
               view?.showData(data.genres!!)
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