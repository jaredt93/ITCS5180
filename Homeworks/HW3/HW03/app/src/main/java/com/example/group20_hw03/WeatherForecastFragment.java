package com.example.group20_hw03;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group20_hw03.databinding.FragmentWeatherForecastBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WeatherForecastFragment extends Fragment {
    FragmentWeatherForecastBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private Data.City city;
    ArrayList<Forecast> forecasts = new ArrayList<>();
    ForecastAdapter adapter;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(Data.City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (Data.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        getActivity().setTitle("Weather Forecast");
        binding.textViewCity.setText(city.toString());
        adapter = new ForecastAdapter(getContext(), R.layout.forecast_row_item, forecasts);
        binding.listView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrieveForecasts();
    }

    // Retrieves the forecasts
    private void retrieveForecasts() {
        String apiKey = "4dd8a13a7fed46fd2d105b8c24ef3b08";
        String units = "imperial";

        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("forecast")
                .addQueryParameter("q", city.getCity())
                .addQueryParameter("appid", apiKey)
                .addQueryParameter("units", units)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Forecast forecast;

                    try {
                        JSONObject json = new JSONObject(body);
                        JSONArray listArray = json.getJSONArray("list");

                        for(int i = 0; i < listArray.length(); i++) {
                            forecast = new Forecast();
                            JSONObject forecastJson = listArray.getJSONObject(i);
                            forecast.setDateTime(forecastJson.getString("dt_txt"));

                            JSONObject mainJson = forecastJson.getJSONObject("main");
                            forecast.setTemp(mainJson.getDouble("temp"));
                            forecast.setTempMax(mainJson.getDouble("temp_max"));
                            forecast.setTempMin(mainJson.getDouble("temp_min"));
                            forecast.setHumidity(mainJson.getInt("humidity"));

                            JSONArray weatherArray = forecastJson.getJSONArray("weather");
                            JSONObject weatherJson = weatherArray.getJSONObject(0);
                            forecast.setDescription(weatherJson.getString("description"));
                            forecast.setWeatherIcon(weatherJson.getString("icon"));

                            forecasts.add(forecast);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}