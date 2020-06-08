package com.example.movieapp.screen.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.ResultsItem
import kotlinx.android.synthetic.main.item_genre.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(var ListMovie: List<ResultsItem?>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie, viewGroup, false))
    }

    override fun getItemCount(): Int = ListMovie.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindGenre(ListMovie[position]!!)
        val item = ListMovie[position]!!
        holder.itemView.setOnClickListener{
            listener.onClick(it, item)
        }
    }

    interface OnItemClickListener {
        fun onClick(view: View, itemView: ResultsItem)
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val movieName = itemView.movie_name
        fun bindGenre(movieResponse: ResultsItem) {
            movieName.text = movieResponse.name
        }
    }
}