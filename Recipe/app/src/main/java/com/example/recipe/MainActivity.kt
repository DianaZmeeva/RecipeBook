package com.example.recipe

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        var recipeList = ArrayList<Recipe>()
        val parentView = findViewById<View>(R.id.parentLayout)
        val listView = findViewById<View>(R.id.listView) as ListView

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Snackbar.make(
                parentView,
                recipeList[position].getName() + " => " + recipeList[position].getDescription(),
                Snackbar.LENGTH_LONG
            ).show()
        }

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            if (InternetConnection.checkConnection(applicationContext)) {
                val dialog: ProgressDialog
                dialog = ProgressDialog(this@MainActivity)
                dialog.setTitle(getString(R.string.jsonWaitString))
                dialog.setMessage(getString(R.string.jsonMessageString))
                dialog.show()

                val api = RetroClient.getApiService()
                val call = api.getMyJSON()
                call.enqueue(object : Callback<RecipeList> {
                    override fun onResponse(call: Call<RecipeList>, response: Response<RecipeList>) {
                        //Dismiss Dialog
                        dialog.dismiss()

                        if (response.isSuccessful) {
                            recipeList = response.body().getRecipes()
                            var adapter = RecipeAdapter(this@MainActivity, recipeList)
                            listView.adapter = adapter

                        } else {
                            Snackbar.make(parentView, R.string.failString, Snackbar.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<RecipeList>, t: Throwable) {
                        dialog.dismiss()
                    }
                })
            } else {
                Snackbar.make(parentView, R.string.internetProblemString, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
