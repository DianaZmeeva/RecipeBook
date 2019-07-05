package com.example.recipe

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class RecipeList {
    @SerializedName("recipes")
    @Expose

    private var recipes = ArrayList<Recipe>()


    fun getRecipes(): ArrayList<Recipe> {
        return recipes
    }
    fun setRecipes(recipes: ArrayList<Recipe>) {
        this.recipes = recipes
    }
}