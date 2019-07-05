package com.example.recipe

import android.content.Context
import android.net.ConnectivityManager

class InternetConnection {
    fun checkConnection(context: Context): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
    }
}