package com.example.newsten;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    List<Articles> articles;
    private MyNewsListener myNewsListener;

    interface MyNewsListener {

        void OnNewsClicked(int position,View view);
    }

    public NewsAdapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    public void setMyNewsListener(MyNewsListener myNewsListener){

        this.myNewsListener=myNewsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //set new object and get it from postion
        Articles a = articles.get(position);
        holder.tvTitle.setText(a.getTitle());
        holder.tvDate.setText(a.getPublishedAt());
        holder.tvDate.setText("\u2022"+dateTime(a.getPublishedAt()));
        holder.tvSource.setText(a.getSource().getName());
        holder.tvSource.setAlpha(0.5f);

        String imageUrl = a.getUrlToImage();
        Glide.with(holder.imageView.getContext()).load(imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return articles.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView tvTitle,tvSource,tvDate;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle= itemView.findViewById(R.id.tv_title);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvDate= itemView.findViewById(R.id.tv_date);
            imageView=itemView.findViewById(R.id.articles_image);
            cardView= itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (myNewsListener!=null)
                        myNewsListener.OnNewsClicked(getAdapterPosition(),view);
                }
            });

        }
    }

//Make new time format for the article
    public String dateTime(String str){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null ;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.getDefault());
            Date date = simpleDateFormat.parse(str);
            time = prettyTime.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
         return time;
        }


    public  String getCountry() {

        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

}
