package com.example.movieapp.screen.genre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.model.GenreResponse
import com.example.movieapp.model.GenresItem
import com.example.movieapp.screen.movie.MovieActivity
import com.example.movieapp.utilities.BaseActivity
import kotlinx.android.synthetic.main.activity_genre.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreActivity : BaseActivity() , GenreContracts.view{

    var presenter: GenrePresenterImpl? = GenrePresenterImpl(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)
        initialLoad()

    }

    fun initialLoad() {
        coroutineScope.launch(Dispatchers.Main) {
            if(isInternetAvailable()) {
                presenter?.loadGenre()
            } else {
                showToast("Maaf Koneksi Tidak Tersedia")
            }
        }

    }

    override fun showData(data: List<GenresItem?>) {
        val genreAdapter = GenreAdapter(data)
        rvGenre.apply {
            layoutManager = LinearLayoutManager(this@GenreActivity)
            adapter = genreAdapter
        }

        genreAdapter.setItemClickListener(object:
        GenreAdapter.OnItemClickListener {
            override fun onClick(view: View, itemView: GenresItem) {
                openMovieByGenre(
                    itemView.id,
                    itemView.name
                )
            }
        })

    }

    fun openMovieByGenre(id:Int?, name:String?) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra("genreId",id)
        intent.putExtra("genreName", name)
        startActivity(intent)
    }

    override fun showToast(params: String) {
        Toast.makeText(this, params, Toast.LENGTH_LONG).show()
    }
}