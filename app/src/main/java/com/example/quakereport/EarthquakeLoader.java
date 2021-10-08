package com.example.quakereport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String urlString;
    public EarthquakeLoader(@NonNull Context context, String urlString) {
        super(context);
        this.urlString=urlString;
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        URL url = QueryUtils.createUrl(urlString);
        String jsonString = QueryUtils.makeHttpRequest(url);
        return QueryUtils.extractEarthquakes(jsonString);
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
