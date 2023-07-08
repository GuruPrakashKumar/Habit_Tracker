package com.example.habbittrackerapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
public class HomeFragment extends Fragment {
    private List<Habit> habitList;
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private HabitDBHelper habitDBHelper;
    private ImageButton addHabitButton;
    private ProgressBar progressBar;
    private TextView progress_textView;
    private TextView homeFragProfileDescription;
    private TextView progress_bar_motivation_textview;
    private int totalHabits;
    private int selectedHabits;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedProfileName = sharedPreferences.getString("profileName", "Save Your Name");
        TextView profileFragProfileName = view.findViewById(R.id.homeFragProfileName);
        profileFragProfileName.setText("Hey "+storedProfileName+" !");

        progressBar = view.findViewById(R.id.progressBar);
        progress_textView = view.findViewById(R.id.progress_textView);
        homeFragProfileDescription = view.findViewById(R.id.homeFragProfileDescription);
        progress_bar_motivation_textview = view.findViewById(R.id.progress_bar_motivation_textview);

        habitDBHelper = new HabitDBHelper(getActivity());
        habitList = habitDBHelper.getAllHabits();


        //recycler view for habits
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        habitAdapter = new HabitAdapter(habitList,habitDBHelper,progressBar,progress_textView,progress_bar_motivation_textview,homeFragProfileDescription);
        recyclerView.setAdapter(habitAdapter);

        // Calculating the initial progress based on selected habits
        calculateProgress();


        addHabitButton = view.findViewById(R.id.addHabitButton);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddHabitDialog();
            }
        });

        return view;


    }

    private void showAddHabitDialog() {
        Dialog habitAdderDialog = new Dialog(getActivity());
        habitAdderDialog.setContentView(R.layout.dialog_habit_adder);
        habitAdderDialog.setCancelable(false);
        TextInputEditText habitNameEditText = habitAdderDialog.findViewById(R.id.habitName);
        TextInputEditText habitDescriptionEditText = habitAdderDialog.findViewById(R.id.habitDescription);
        Button btnSave = habitAdderDialog.findViewById(R.id.btnSave);
        Button btnCancel = habitAdderDialog.findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String habitNameString = habitNameEditText.getText().toString();
                String habitDescriptionString = habitDescriptionEditText.getText().toString();
                if(TextUtils.isEmpty(habitNameString)){
                    habitNameEditText.setError("Name cannot be empty");
                    return;
                }else{

                    habitDBHelper.insertHabit(habitNameString, habitDescriptionString, R.drawable.baseline_menu_24, 0,20);

                    // Refresh the habit list from the database
                    habitList.clear();
                    habitList.addAll(habitDBHelper.getAllHabits());
                }
                habitAdderDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitAdderDialog.dismiss();
            }
        });
        habitAdderDialog.show();

    }

    private void updateProgress() {
        int totalHabits = habitList.size();
        int selectedHabits = 0;
        for (Habit habit : habitList) {
            if (habit.isSelected()) {
                selectedHabits++;
            }
        }
        if(totalHabits==selectedHabits){
            homeFragProfileDescription.setText("You have Completed all tasks Today");
        }else{
            homeFragProfileDescription.setText("You have Completed "+selectedHabits+" tasks Today");
        }
        int progress = (int) ((selectedHabits / (float) totalHabits) * 100);

        progressBar.setProgress(progress);
        progress_textView.setText(progress+"%");
        if(progress<=50){
            progress_bar_motivation_textview.setText("Work Hard !");
        }else if(progress<100){
            progress_bar_motivation_textview.setText("Keep Going !");
        }else{
            progress_bar_motivation_textview.setText("Well Done !");
        }
    }

    private void updateSelectedHabitsCount(boolean isSelected) {
        if (isSelected) {
            selectedHabits++;
        } else {
            selectedHabits--;
        }
        updateProgress();
    }
    private void calculateProgress() {
        totalHabits = habitList.size();
        selectedHabits = 0;

        for (Habit habit : habitList) {
            if (habit.isSelected()) {
                selectedHabits++;
            }
        }

        updateProgress();
    }

    private void setCheckboxClickListener(CheckBox checkBox) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSelectedHabitsCount(isChecked);
            }
        });
    }



}