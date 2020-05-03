package com.example.newsten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detailed extends AppCompatActivity {


    TextView titleTv,authorTv,sourceTv,descriptionTv,contentTv;
    Button backBtn;
    WebView webView;
    LinearLayout deatiledLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        webView=findViewById(R.id.web_view);

        deatiledLinear=findViewById(R.id.detailed_linear);


        backBtn=findViewById(R.id.back_btn);
        backBtn.setAlpha(0.6f);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detailed.this,MainActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sps = PreferenceManager.getDefaultSharedPreferences(this);

 /*       titleTv=findViewById(R.id.title_txt);
        authorTv=findViewById(R.id.author_txt);
        sourceTv=findViewById(R.id.source_txt);
        descriptionTv=findViewById(R.id.title_desc);
        contentTv=findViewById(R.id.content_txt);
        imageView=findViewById(R.id.image_tv);

        titleTv.setText(getIntent().getStringExtra("title"));
        descriptionTv.setText(getIntent().getStringExtra("description"));
        authorTv.setText(getIntent().getStringExtra("author"));
        contentTv.setText(getIntent().getStringExtra("content"));
        sourceTv.setText(getIntent().getStringExtra("source"));*/

        /*Glide.with(imageView.getContext()).load(getIntent().getStringExtra("url_image")).into(imageView);*/

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(sps.getString("url","url"));
        webView.loadUrl(getIntent().getStringExtra("url"));




    }
}
