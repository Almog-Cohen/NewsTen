package com.example.newsten;

        import retrofit2.Call;
        import retrofit2.http.GET;
        import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<HeadLines> getHeadLines(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<HeadLines> getHeadLines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

}
