package com.example.habbittrackerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopHabitsAdapter extends RecyclerView.Adapter<TopHabitsAdapter.TopHabitsViewHolder> {

    private List<Habit> habitList;

    public TopHabitsAdapter(List<Habit> habitList) {
        this.habitList = habitList;
    }

    @NonNull
    @Override
    public TopHabitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit_week_progress, parent, false);
        return new TopHabitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopHabitsViewHolder holder, int position) {
        Habit habit = habitList.get(position);

        holder.textViewHabit.setText(habit.getName());
        holder.textViewDescription.setText(habit.getDescription());
        holder.icon.setImageResource(habit.getIconResId());

        holder.progress_Bar.setProgress(habit.getProgress());
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public class TopHabitsViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress_Bar;
        public TextView textViewHabit;
        public TextView textViewDescription;
        public ImageView icon;

        public TopHabitsViewHolder(@NonNull View itemView) {
            super(itemView);
            progress_Bar = itemView.findViewById(R.id.progress_bar);
            textViewHabit = itemView.findViewById(R.id.textViewHabit);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}

