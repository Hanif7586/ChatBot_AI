package com.hrtsoft.new_chatgpt;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwner;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText questionInput;
    private Button searchButton;
    private TextView answerText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionInput = findViewById(R.id.question_input);
        searchButton = findViewById(R.id.search_button);
        answerText = findViewById(R.id.answer_text);
        progressBar = findViewById(R.id.progress_bar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.hy-tech.my.id/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GeminiApiService geminiApiService = retrofit.create(GeminiApiService.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionInput.getText().toString();
                if (!question.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);

                    Call<GeminiResponse> call = geminiApiService.getAnswer(question);
                    call.enqueue(new Callback<GeminiResponse>() {
                        @Override
                        public void onResponse(Call<GeminiResponse> call, Response<GeminiResponse> response) {
                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                GeminiResponse geminiResponse = response.body();
                                if (geminiResponse != null) {
                                    answerText.setText(geminiResponse.getText());
                                } else {
                                    answerText.setText("");
                                }
                            } else {
                                answerText.setText(getString(R.string.error));
                            }
                        }

                        @Override
                        public void onFailure(Call<GeminiResponse> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            answerText.setText(getString(R.string.error));
                        }
                    });
                }
            }
        });
    }
}