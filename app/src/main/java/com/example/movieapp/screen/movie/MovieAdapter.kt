package com.example.movieapp.screen.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.ResultsItem
import kotlinx.android.synthetic.main.item_genre.view.*

class MovieAdapter(var ListGenre: List<ResultsItem?>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_genre, viewGroup, false))
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
        fun onClick(view: View, itemView: ResultsItem)
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun refreshAdapter(resultMovie: List<ResultsItem?>) {
        Log.e("message","resultMovie $resultMovie ListGenre $ListGenre")
        ListGenre.toMutableList().add(resultMovie[0])
        notifyDataSetChanged()

    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val genreName = itemView.genre_name

        fun bindGenre(genreResponse: ResultsItem) {
            genreName.text = genreResponse.name
        }
    }
}