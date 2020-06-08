package com.example.movieapp.utilities

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

open class BaseActivity: AppCompatActivity() {

    suspend fun isInternetAvailable() : Boolean = withContext(Dispatchers.Default) {
        Utils.isInternetAvailable()
    }
    var job = Job()
    val coroutineScope = CoroutineScope(Dispatchers.Main + job)
}