package com.example.newsten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    RecyclerView newsRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsAdapter newsAdapter;
    final String API_KEY = "051f2230d01c4235abeff3f4aef74452";
    List<Articles> articles = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.news_fragment,container,false);
        //Make our view with swipe refresh
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        newsRecyclerView = view.findViewById(R.id.news_recyclerview);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                retrieveJson(API_KEY);
            }
        });

        retrieveJson(API_KEY);

        return view;
    }

    //Getting the last articles by country,category
    public void retrieveJson(String apiKey){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String country = sp.getString("list_preference_countries","");
        if (country.equals(""))
            country="us";

        swipeRefreshLayout.setRefreshing(true);

        Call<HeadLines> call = ApiClient.getInstance().getApi().getHeadLines(country,apiKey);
        call.enqueue(new Callback<HeadLines>() {
            @Override
            public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {

                if (response.isSuccessful() && response.body().getArticles() != null){

                    swipeRefreshLayout.setRefreshing(false);

                    articles.clear();
                    articles = response.body().getArticles();

                    newsAdapter = new NewsAdapter(getActivity(),articles);
                    newsAdapter.setMyNewsListener(new NewsAdapter.MyNewsListener() {
                        @Override
                        public void OnNewsClicked(int position, View view) {

                            Articles a = articles.get(position);
                            Intent intent = new Intent(getActivity(),Detailed.class);
                            intent.putExtra("url",a.getUrlAdress());

                            startActivity(intent);
                        }
                    });
                    newsRecyclerView.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}



