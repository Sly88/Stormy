package com.sylwestermichalowski.stormy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    public  static  final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "592e57b967b511d892ed38ccf0ddbe1e";
        double latitude = 37.8267;
        double longitude = -122.423;
        String url = "https://api.forecast.io/forecast/"+apiKey +"/" + latitude + "," + longitude;

        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    if(response.isSuccessful())
                    {
                        Log.v(TAG, response.body().string());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
