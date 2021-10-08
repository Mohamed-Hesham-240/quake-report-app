package com.example.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    //creating the constructor
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> list) {
        super(context, 0, list);
    }
    //helper method for matting date
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    //helper method for formatting time
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    //helper method for formatting time
    private String formatMag(double mag) {
        DecimalFormat formatter = new DecimalFormat("#0.0");
        return formatter.format(mag);
    }
    //helper method for getting the mag color
    private int getMagnitudeColor(double dMag){
        int iMag = (int)Math.floor(dMag);
        switch (iMag){
            case 0:
            case 1:
                return ContextCompat.getColor(getContext(),R.color.magnitude1);
            case 2:
                return ContextCompat.getColor(getContext(),R.color.magnitude2);
            case 3:
                return ContextCompat.getColor(getContext(),R.color.magnitude3);
            case 4:
                return ContextCompat.getColor(getContext(),R.color.magnitude4);
            case 5:
                return ContextCompat.getColor(getContext(),R.color.magnitude5);
            case 6:
                return ContextCompat.getColor(getContext(),R.color.magnitude6);
            case 7:
                return ContextCompat.getColor(getContext(),R.color.magnitude7);
            case 8:
                return ContextCompat.getColor(getContext(),R.color.magnitude8);
            case 9:
                return ContextCompat.getColor(getContext(),R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(),R.color.magnitude10plus);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Earthquake currentEarthquake = getItem(position);
        View listItemView = convertView;
        //if there is now scrap views, inflate a new view according to list_item layout
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        //setting the mag
        TextView mag = listItemView.findViewById(R.id.mag);
        mag.setText(formatMag(currentEarthquake.getMag()));
        //setting the mag background
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();  //to get the background of the textView (drawable)
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMag());         //you pass in the mag value and gets the number representing the color
        magnitudeCircle.setColor(magnitudeColor);                                   //setting the background color of that drawable
        //setting the place
        TextView placeTextView = listItemView.findViewById(R.id.place);
        TextView offsetTextView = listItemView.findViewById(R.id.offest);
        String place = currentEarthquake.getPlace();
        String offset="";
        String mainPlace=place;
        if (place.contains("of")){
            boolean isOfFound=false;
            mainPlace="";
            String[] splitArray = place.split(" ");
            int j=0;
            for (int i=0; !isOfFound;++i){
                offset=offset+splitArray[i]+" ";
                if (splitArray[i].equalsIgnoreCase("of"))
                {isOfFound=true; j=i+1;}

            }
            for (int i=j; i<splitArray.length;++i){
                mainPlace=mainPlace+splitArray[i]+" ";
            }
            offset.trim();
            mainPlace.trim();
        }
        else{
            offset="Near the";
        }
        placeTextView.setText(mainPlace);
        offsetTextView.setText(offset);
        //setting the date and time
        TextView dateTextView = listItemView.findViewById(R.id.date);
        TextView timeTextView = listItemView.findViewById(R.id.time);
        long unixTime = currentEarthquake.getUnixDate();
        Date date = new Date(unixTime);
        dateTextView.setText(formatDate(date));
        timeTextView.setText(formatTime(date));

        return listItemView;
    }
}
