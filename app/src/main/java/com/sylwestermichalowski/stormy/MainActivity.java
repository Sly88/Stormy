package com.sylwestermichalowski.stormy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    public  static  final String TAG = MainActivity.class.getSimpleName();

    private  CurrentWeather mCurrentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "592e57b967b511d892ed38ccf0ddbe1e";

        double latitude = 37.8267;
        double longitude = -122.423;
        String url = "https://api.forecast.io/forecast/"+apiKey +"/" + latitude + "," + longitude;

        if(isNetwortAvaliable())
        {
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            String jsonData = response.body().string();
                            Log.v(TAG, jsonData);
                            mCurrentWeather = getCurrentDetails(jsonData);
                        } else
                            alertUserAboutError();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {

                    }
                }
            });
        }
        else
            Toast.makeText(this, "Network is unavaliable", Toast.LENGTH_LONG).show();


    }

    private CurrentWeather getCurrentDetails(String jsonData) throws  JSONException
    {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTimeZone(timezone);

        Log.d(TAG, currentWeather.getForrmatedTime());

        return  currentWeather;
    }

    private boolean isNetwortAvaliable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvaliable = false;
        if(networkInfo != null && networkInfo.isConnected())
            isAvaliable = true;

        return isAvaliable;
    }

    private void  alertUserAboutError()
    {
        AllertDialogFragment dialog = new AllertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }



}
