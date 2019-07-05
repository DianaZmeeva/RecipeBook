package com.example.recipe;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class test extends AppCompatActivity {

    private ListView listView;
    private View parentView;
    private ArrayList<Recipe> recipeList;
    private Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeList=new ArrayList<>();
        parentView = findViewById(R.id.parentLayout);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(parentView, recipeList.get(position).getName() + " => " + recipeList.get(position).getDescription(), Snackbar.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View view) {
                if (InternetConnection.checkConnection(getApplicationContext())){
                    final ProgressDialog dialog;
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle(getString(R.string.jsonWaitString));
                    dialog.setMessage(getString(R.string.jsonMessageString));
                    dialog.show();

                    ApiService api=RetroClient.getApiService();
                    Call<RecipeList> call=api.getMyJSON();
                    call.enqueue(new Callback<RecipeList>() {
                        @Override
                        public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                            //Dismiss Dialog
                            dialog.dismiss();

                            if (response.isSuccessful()) {
                                recipeList = response.body().getRecipes();
                                adapter = new Adapter(MainActivity.this, recipeList);
                                listView.setAdapter(adapter);

                            } else {
                                Snackbar.make(parentView, R.string.failString, Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RecipeList> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });
                }
                else{
                    Snackbar.make(parentView, R.string.internetProblemString, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
