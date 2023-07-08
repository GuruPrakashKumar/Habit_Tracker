package com.example.habbittrackerapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;


public class HabitsFragment extends Fragment {
    public HabitsFragment() {
        // Required empty public constructor
    }

    public static HabitsFragment newInstance(String param1, String param2) {
        HabitsFragment fragment = new HabitsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habits, container, false);
        BarChart mBarChart = view.findViewById(R.id.barchart);

        mBarChart.clearChart();

        float[] kilometers = {2.3f, 3.0f, 5.3f, 4.0f, 1.7f, 3.0f};
        String[] weeks = {"Week 1", "Week 2", "Week 3", "Week 4", "Week 5", "Week 6"};
        int[] colors = {Color.parseColor("#252e34"), Color.parseColor("#ffe284"),
                Color.parseColor("#FF2A2A"), Color.parseColor("#64b5f6"),
                Color.parseColor("#56B7F1"), Color.parseColor("#7d8c93"),};

        for (int i = 0; i < kilometers.length; i++) {
            BarModel barModel = new BarModel(kilometers[i], colors[i]);
            barModel.setLegendLabel(weeks[i]);
            mBarChart.addBar(barModel);
        }
        mBarChart.startAnimation();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedWeight = sharedPreferences.getString("weight", "55");
        if(storedWeight.equals("")){
            storedWeight = "-1";
        }
        TextView suggestionCycling = view.findViewById(R.id.suggestionCycling);
        if(Integer.parseInt(storedWeight)>60){
            suggestionCycling.setText("According to your weight, you should cover at least 10 km in a day. This is good for your health.");
            suggestionCycling.setPadding(25,0,25,10);
        }else if(Integer.parseInt(storedWeight)>0){
            suggestionCycling.setText("You are doing good, keep it up !!!");
            suggestionCycling.setPadding(25,0,25,10);
        }else{
            suggestionCycling.setText("");
        }




        PieChart mPieChart = (PieChart) view.findViewById(R.id.piechart);
        mPieChart.addPieSlice(new PieModel("Week 1", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Week 2", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Week 3", 35, Color.parseColor("#FF2A2A")));
        mPieChart.addPieSlice(new PieModel("Week 4", 10, Color.parseColor("#FED70E")));
        mPieChart.addPieSlice(new PieModel("Week 5", 20, Color.parseColor("#f4984d")));
        mPieChart.startAnimation();


        ValueLineChart mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);

        series.addPoint(new ValueLinePoint("Week 1", 21.4f));
        series.addPoint(new ValueLinePoint("Week 2", 28.0f));
        series.addPoint(new ValueLinePoint("Week 3", 25.0f));
        series.addPoint(new ValueLinePoint("Week 4", 14.6f));
        series.addPoint(new ValueLinePoint("Week 5", 30.0f));
        series.addPoint(new ValueLinePoint("Week 6", 24.0f));
        series.addPoint(new ValueLinePoint("Week 7", 12.0f));
        series.addPoint(new ValueLinePoint("Week 8", 18.0f));

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();



        return view;
    }
}