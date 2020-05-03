package com.example.newsten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {


    String TAG = "checkTag";
    RecyclerView newsRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsAdapter newsAdapter;
    final String API_KEY = "051f2230d01c4235abeff3f4aef74452";
    List<Articles> articles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_fragment,container,false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        newsRecyclerView = view.findViewById(R.id.news_recyclerview);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

/*
        final String country = getCountry();
*/
       final String country = "us";

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(country,API_KEY);
                Toast.makeText(getContext(), "Refreshing", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("checkTag","before json function");
        retrieveJson(country,API_KEY);
        Log.d("checkTag","finish json function");
        return view;
    }


    public void retrieveJson(String country, String apiKey){


        swipeRefreshLayout.setRefreshing(true);

        Call<HeadLines> call = ApiClient.getInstance().getApi().getHeadLines(country,apiKey);


        call.enqueue(new Callback<HeadLines>() {
            @Override
            public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {

                if (response.isSuccessful() && response.body().getArticles() != null){
                    Log.d("checkTag","Response succuess");
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    Log.d("checkTag","Getting articles");
                    saveNotificationArtilce();


                    newsAdapter = new NewsAdapter(getActivity(),articles);
                    newsAdapter.setMyNewsListener(new NewsAdapter.MyNewsListener() {
                        @Override
                        public void OnNewsClicked(int position, View view) {
                            Articles a = articles.get(position);
                            Intent intent = new Intent(getActivity(),Detailed.class);
                            intent.putExtra("title",a.getTitle());
                            intent.putExtra("description",a.getDescription());
                            intent.putExtra("author",a.getAuthor());
                            intent.putExtra("url_image",a.getUrlToImage());
                            intent.putExtra("content",a.getContent());
                            intent.putExtra("url",a.getUrlAdress());
                            intent.putExtra("source",a.getSource().toString());
                            Log.d("checkTag","before implenting webview");
                            startActivity(intent);
                        }
                    });
                    Log.d("checkTag","Before setting news adapter");
                    newsRecyclerView.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {
                Log.d("checkTag",t.getLocalizedMessage());
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }

    public  String getCountry(){

        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

   public void saveNotificationArtilce(){

       Random rand = new Random();
       int randomArticle=rand.nextInt(articles.size());

       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
       SharedPreferences.Editor editor = sp.edit();
       editor.putString("title",articles.get(randomArticle).getTitle());
       editor.putString("content",articles.get(randomArticle).getContent());
       editor.putString("url",articles.get(randomArticle).getUrlAdress());
       editor.putString("imageUrl",articles.get(randomArticle).getUrlToImage());
       editor.apply();

   }

}



