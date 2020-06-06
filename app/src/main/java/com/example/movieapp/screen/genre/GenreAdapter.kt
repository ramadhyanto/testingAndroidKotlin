package com.example.movieapp.screen.genre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.GenresItem
import kotlinx.android.synthetic.main.item_genre.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class GenreAdapter(var ListGenre: List<GenresItem?>) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie, viewGroup, false))
    }

    override fun getItemCount(): Int = ListGenre.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindGenre(ListGenre[position]!!)
        val item = ListGenre[position]!!
        holder.itemView.setOnClickListener{
            listener.onClick(it, item)
        }
    }

    // on click function
    interface OnItemClickListener {
        fun onClick(view: View, itemView: GenresItem)
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val movieName = itemView.movie_name

        fun bindGenre(movieResponse: GenresItem) {
            movieName.text = movieResponse.name
        }
    }
}