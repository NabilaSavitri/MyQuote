package com.si5a.myquote.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.si5a.myquote.API.APIRequestData;
import com.si5a.myquote.API.RetroServer;
import com.si5a.myquote.Adapter.AdapterQoute;
import com.si5a.myquote.Model.QuoteModel;
import com.si5a.myquote.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvQuote;
    private ProgressBar pbQuote;
    private List<QuoteModel> listQuote;
    private AdapterQoute adQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvQuote = findViewById(R.id.rv_quote);
        pbQuote = findViewById(R.id.pb_qoute);
        rvQuote.setLayoutManager(new LinearLayoutManager(this));
        retrieveData();
    }

    private void retrieveData() {
        pbQuote.setVisibility(View.VISIBLE);
        APIRequestData ARD = RetroServer.connectRetrofit().create(APIRequestData.class);
        Call<List<QuoteModel>> retrieveProcess = ARD.ardRetrieve();

        retrieveProcess.enqueue(new Callback<List<QuoteModel>>() {
            @Override
            public void onResponse(Call<List<QuoteModel>> call, Response<List<QuoteModel>> response) {
                listQuote = response.body();
                adQuote = new AdapterQoute(listQuote, MainActivity.this);
                rvQuote.setAdapter(adQuote);
                pbQuote.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<QuoteModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection Error! Cannot Reach Server!", Toast.LENGTH_SHORT).show();
                pbQuote.setVisibility(View.GONE);

            }
        });

    }

}