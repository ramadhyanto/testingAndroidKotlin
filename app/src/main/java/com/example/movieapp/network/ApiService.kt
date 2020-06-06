package com.example.movieapp.network

import com.example.movieapp.model.DetailMovieResponse
import com.example.movieapp.model.GenreResponse
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.ReviewResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET("genre/movie/list?")
    fun getAllGenreMovie(@Query("api_key") api_key: String): Single<GenreResponse>

    @GET("movie/{genreId}/lists?")
    fun getAllMovie(@Path("genreId", encoded = true) genreId: Int, @Query("api_key") api_key: String, @Query("page") page: Int ): Single<MovieResponse>

    @GET("movie/{movieId}?")
    fun getDetailMovie(@Path("movieId", encoded = true) movieId: Int, @Query("api_key") api_key: String ): Single<DetailMovieResponse>

    @GET("movie/{movieId}/reviews?")
    fun getReview(@Path("movieId", encoded = true) movieId: Int, @Query("api_key") api_key: String ): Single<ReviewResponse>
}