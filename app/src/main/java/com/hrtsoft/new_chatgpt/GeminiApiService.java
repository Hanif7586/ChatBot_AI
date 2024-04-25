package com.hrtsoft.new_chatgpt;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GeminiApiService {
    @GET("gemini/{question}")
    Call<GeminiResponse> getAnswer(@Path("question") String question);
}


