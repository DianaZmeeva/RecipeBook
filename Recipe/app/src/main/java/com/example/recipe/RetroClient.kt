package com.example.recipe

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroClient {
    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://test.kode-t.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiService(): ApiService {
        return getRetrofitInstance().create(ApiService::class.java)
    }
}