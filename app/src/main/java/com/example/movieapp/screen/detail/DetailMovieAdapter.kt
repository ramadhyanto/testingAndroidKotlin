package com.example.movieapp.screen.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.ResultsReviewItem
import kotlinx.android.synthetic.main.item_movie.view.*

class DetailMovieAdapter(var ListReview: List<ResultsReviewItem?>) : RecyclerView.Adapter<DetailMovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_review, viewGroup, false))
    }

    override fun getItemCount(): Int = ListReview.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(ListReview[position]!!)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val movieName = itemView.movie_name

        fun bindMovie(reviewResponse: ResultsReviewItem) {
            movieName.text = reviewResponse.content
        }
    }
}