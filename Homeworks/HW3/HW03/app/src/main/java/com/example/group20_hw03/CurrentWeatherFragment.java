package com.example.group20_hw03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group20_hw03.databinding.FragmentCurrentWeatherBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CurrentWeatherFragment extends Fragment {
    FragmentCurrentWeatherBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private Data.City city;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(Data.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
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
        retrieveCurrentWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);
        getActivity().setTitle("Current Weather");
        binding.textViewCity.setText(city.toString());
        retrieveCurrentWeather();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCheckForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoWeatherForecast(city);
            }
        });
    }

    // Retrieves the current weather
    private void retrieveCurrentWeather() {
        String apiKey = "4dd8a13a7fed46fd2d105b8c24ef3b08";
        String units = "imperial";

        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("weather")
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
                    Weather weather = new Weather();

                    try {
                        JSONObject json = new JSONObject(body);

                        JSONArray weatherArray = json.getJSONArray("weather");
                        JSONObject weatherJson = weatherArray.getJSONObject(0);
                        weather.setDescription(weatherJson.getString("description"));
                        weather.setWeatherIcon(weatherJson.getString("icon"));

                        JSONObject mainJson = json.getJSONObject("main");
                        weather.setTemp(mainJson.getDouble("temp"));
                        weather.setTempMax(mainJson.getDouble("temp_max"));
                        weather.setTempMin(mainJson.getDouble("temp_min"));
                        weather.setHumidity(mainJson.getInt("humidity"));

                        JSONObject windJson = json.getJSONObject("wind");
                        weather.setWindSpeed(windJson.getDouble("speed"));
                        weather.setWindDegree(windJson.getInt("deg"));

                        JSONObject cloudsJson = json.getJSONObject("clouds");
                        weather.setCloudiness(cloudsJson.getInt("all"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DecimalFormat df1 = new DecimalFormat(".#");
                            DecimalFormat df2 = new DecimalFormat(".00");

                            binding.temp.setText(df1.format(weather.getTemp()) + " °F");
                            binding.tempMax.setText(df1.format(weather.getTempMax()) + " °F");
                            binding.tempMin.setText(df1.format(weather.getTempMin())+ " °F");
                            binding.description.setText(weather.getDescription());
                            binding.humidity.setText(weather.getHumidity() + "%");
                            binding.windSpeed.setText(df2.format(weather.getWindSpeed()) + " miles/hr");
                            binding.windDegree.setText(weather.getWindDegree() + " degrees");
                            binding.cloudiness.setText(weather.getCloudiness() + "%");

                            String urlIcon = "https://openweathermap.org/img/wn/" + weather.getWeatherIcon() +"@2x.png";
                            Picasso.get().load(urlIcon).into(binding.imageViewWeatherIcon);
                        }
                    });
                }
            }
        });
    }


    // Listener
    CurrentWeatherFragment.CurrentWeatherListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof CurrentWeatherFragment.CurrentWeatherListener) {
            mListener = (CurrentWeatherFragment.CurrentWeatherListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement CurrentWeatherListener"));
        }
    }

    public interface CurrentWeatherListener {
        void gotoWeatherForecast(Data.City city);
    }
}