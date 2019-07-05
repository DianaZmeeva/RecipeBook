package com.example.recipe

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("recipes.json")
    fun getMyJSON(): Call<RecipeList>
}