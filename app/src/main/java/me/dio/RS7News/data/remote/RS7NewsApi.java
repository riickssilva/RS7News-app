package me.dio.RS7News.data.remote;

import java.util.List;

import me.dio.RS7News.domain.News;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RS7NewsApi {

    @GET("news.json")
    Call<List<News>> getNews();
}
