package com.example.kisaanonline.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kisaanonline.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

public class HomeFragment extends Fragment {
    private RangeSeekBar priceSeekBar;
    private TextView priceRange;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);

        //Get References
        priceRange=v.findViewById(R.id.price_range);
        priceSeekBar=v.findViewById(R.id.price_seek_bar);

        initialisePriceSeekBar(100,4000);

        return v;
    }

    private void initialisePriceSeekBar(int minValue, int maxValue) {

        priceSeekBar.setRangeValues(100,4000);
        priceSeekBar.setOnRangeSeekBarChangeListener(
                new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                    @Override
                    public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                        priceRange.setText("Filter: Rs. " + minValue + "- Rs. " + maxValue);
                    }
                }
        );
    }

}
