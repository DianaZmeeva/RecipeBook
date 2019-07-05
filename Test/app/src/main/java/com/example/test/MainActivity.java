package com.example.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private View parentView;
    private ArrayList<Recipe> recipeList;
    private Adapter adapter;
    private int sorted=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeList = new ArrayList<>();

        parentView = findViewById(R.id.parentLayout);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);

                intent.putExtra("recipeObject", recipeList.get(position));
                startActivity(intent);
            }
        });

        update();

        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        });
    }

    private void update() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(getString(R.string.jsonWaitString));
            dialog.setMessage(getString(R.string.jsonMessageString));
            dialog.show();

            ApiService api = RetroClient.getApiService();
            Call<RecipeList> call = api.getMyJSON();

            call.enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
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

        } else {
            Snackbar.make(parentView, R.string.internetProblemString, Snackbar.LENGTH_LONG).show();
        }
    }

    public void SortFunction(View view) {
        if (sorted!=1){
            adapter.sort(new Comparator<Recipe>() {
                @Override
                public int compare(Recipe s1, Recipe s2) {
                    return s1.getName().compareTo(s2.getName());
                }
            });
            sorted=1;
        }
        else{
            adapter.sort(new Comparator<Recipe>() {
                @Override
                public int compare(Recipe s1, Recipe s2) {
                    return (s1.getLastUpdated() < s2.getLastUpdated()) ? -1 : ((s1.getLastUpdated() == s2.getLastUpdated()) ? 0 : 1);
                }
            });
            sorted=2;
        }
        adapter.notifyDataSetChanged();
    }
}
