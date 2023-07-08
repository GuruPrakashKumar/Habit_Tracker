package com.example.habbittrackerapp;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private List<Habit> habitList;
    private ProgressBar progressBar;
    private TextView progress_textView;
    private TextView progress_bar_motivation_textview;
    private TextView homeFragProfileDescription;
    private HabitDBHelper habitDBHelper;

    public HabitAdapter(List<Habit> habitList, HabitDBHelper dbHelper,ProgressBar progressBar,TextView progress_textView,TextView progress_bar_motivation_textview,TextView homeFragProfileDescription) {
        this.habitList = habitList;
        this.progressBar = progressBar;
        this.progress_textView = progress_textView;
        this.habitDBHelper = dbHelper;
        this.progress_bar_motivation_textview = progress_bar_motivation_textview;
        this.homeFragProfileDescription = homeFragProfileDescription;


    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.textViewHabit.setText(habit.getName());
        holder.textViewDescription.setText(habit.getDescription());
        holder.icon.setImageResource(habit.getIconResId());
        holder.checkBox.setChecked(habit.isSelected());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showUpdateHabitDialog(habit);
                //Refresh the habit list from the database
                habitList.clear();
                habitList.addAll(habitDBHelper.getAllHabits());
                return false;
            }
        });


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                habit.setSelected(isChecked);
                habitDBHelper.updateHabitSelection(habit.getId(), isChecked);
                updateProgress();
            }
        });
    }
    private void showUpdateHabitDialog(Habit habit) {
        Dialog habitUpdaterDialog = new Dialog(homeFragProfileDescription.getContext());
        habitUpdaterDialog.setContentView(R.layout.dialog_habit_updater);
        habitUpdaterDialog.setCancelable(false);
        TextInputEditText habitNameEditText = habitUpdaterDialog.findViewById(R.id.habitName);
        TextInputEditText habitDescriptionEditText = habitUpdaterDialog.findViewById(R.id.habitDescription);
        Button btnSave = habitUpdaterDialog.findViewById(R.id.btnSave);
        Button btnCancel = habitUpdaterDialog.findViewById(R.id.btnCancel);
        ImageButton btnDelete = habitUpdaterDialog.findViewById(R.id.btnDelete);

        habitNameEditText.setText(habit.getName());
        habitDescriptionEditText.setText(habit.getDescription());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String habitNameString = habitNameEditText.getText().toString();
                String habitDescriptionString = habitDescriptionEditText.getText().toString();
                if(TextUtils.isEmpty(habitNameString)){
                    habitNameEditText.setError("Name cannot be empty");
                    return;
                }else{
                    habitDBHelper.updateHabit(habit.getId(),habitNameString, habitDescriptionString);
                    Toast.makeText(homeFragProfileDescription.getContext(), "Habit Updated", Toast.LENGTH_SHORT).show();
                }
                habitUpdaterDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitUpdaterDialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitDBHelper.deleteHabit(habit.getId());
                Toast.makeText(homeFragProfileDescription.getContext(), "Habit Deleted", Toast.LENGTH_SHORT).show();
                habitUpdaterDialog.dismiss();
            }
        });
        habitUpdaterDialog.show();

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
    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public class HabitViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHabit;
        public TextView textViewDescription;
        public CheckBox checkBox;
        public ImageView icon;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHabit = itemView.findViewById(R.id.textViewHabit);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            checkBox = itemView.findViewById(R.id.checkBox);
            icon = itemView.findViewById(R.id.icon);
        }
    }

}
