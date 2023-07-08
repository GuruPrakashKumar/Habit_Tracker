package com.example.habbittrackerapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProgressFragment extends Fragment {

    public ProgressFragment() {
        // Required empty public constructor
    }


    public static ProgressFragment newInstance(String param1, String param2) {
        ProgressFragment fragment = new ProgressFragment();
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

        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        HabitDBHelper habitDBHelper = new HabitDBHelper(getActivity());
        List<Habit> topHabits = habitDBHelper.getTopHabits();
        List<Habit> worstHabits = habitDBHelper.getWorstHabits();


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedProfileName = sharedPreferences.getString("profileName", "Save Your Name");
        TextView profileFragProfileName = view.findViewById(R.id.progressFragProfileName);
        profileFragProfileName.setText("Hey "+storedProfileName+" !");

        // RecyclerView with the TopHabitsAdapter
        RecyclerView recyclerView = view.findViewById(R.id.topHabitsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TopHabitsAdapter topHabitsAdapter = new TopHabitsAdapter(topHabits);
        recyclerView.setAdapter(topHabitsAdapter);

        // RecyclerView with the WorstHabitsAdapter
        RecyclerView recyclerView2 = view.findViewById(R.id.worstHabitsRecyclerView);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        TopHabitsAdapter worstHabitsAdapter = new TopHabitsAdapter(worstHabits);
        recyclerView2.setAdapter(worstHabitsAdapter);

        return view;
    }
}