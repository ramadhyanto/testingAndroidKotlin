package com.example.movieapp.utilities

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object Utils {

    fun isInternetAvailable(): Boolean {
        try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)

            sock.connect(sockaddr, timeoutMs)
            sock.close()
            return true
        } catch (e: IOException) {
            return false
        }

    }
}