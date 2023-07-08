package com.example.habbittrackerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HabitDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HabitDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_HABIT = "habits";
    private static final String KEY_ID = "id";
    private static final String KEY_HABIT_NAME = "habit_name";
    private static final String KEY_HABIT_DESC = "habit_desc";
    private static final String KEY_HABIT_ICON_RES_ID = "habit_icon_id";
    private static final String KEY_HABIT_SELECTED = "habit_selected";

    private static final String KEY_HABIT_PROGRESS = "habit_progress";



    public HabitDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_HABIT+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_HABIT_NAME+" TEXT, "+
                KEY_HABIT_DESC+" TEXT, "+
                KEY_HABIT_ICON_RES_ID+" INTEGER, "+
                KEY_HABIT_SELECTED+" INTEGER, "+
                KEY_HABIT_PROGRESS+" INTEGER"+
                ")");
        insertPreAddedHabits(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HABIT);
        onCreate(db);
    }

    public void insertHabit(String habitName, String habitDesc, int iconResId, int selected,int progress){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HABIT_NAME,habitName);
        values.put(KEY_HABIT_DESC,habitDesc);
        values.put(KEY_HABIT_ICON_RES_ID,iconResId);
        values.put(KEY_HABIT_SELECTED,selected);
        values.put(KEY_HABIT_PROGRESS,progress);
        db.insert(TABLE_HABIT,null,values);
    }

    public void deleteHabit(long habitId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HABIT, KEY_ID + " = ?", new String[]{String.valueOf(habitId)});
    }
    public void updateHabit(int id,String name,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_HABIT_NAME,name);
        cv.put(KEY_HABIT_DESC,description);
        db.update(TABLE_HABIT,cv,KEY_ID+" = ?",new String[]{String.valueOf(id)});
    }


    public List<Habit> getAllHabits() {
        List<Habit> habitList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_HABIT, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_HABIT_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_HABIT_DESC));
                int iconResId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_ICON_RES_ID));
                boolean selected;
                selected = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_SELECTED)) == 1;
                int progress = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_PROGRESS));
                Habit habit = new Habit(id, name, description, iconResId, selected,progress);
                habitList.add(habit);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return habitList;
    }

    public List<Habit> getTopHabits() {
        List<Habit> habitList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_HABIT, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_HABIT_NAME));
                String description = "Good! you are good at "+name+" this week";
                int iconResId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_ICON_RES_ID));
                boolean selected;
                selected = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_SELECTED)) == 1;
                int progress = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_PROGRESS));
                Habit habit = new Habit(id, name, description, iconResId, selected,progress);
                if(id<=4){
                    habitList.add(habit);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return habitList;
    }

    public List<Habit> getWorstHabits() {
        List<Habit> habitList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_HABIT, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_HABIT_NAME));
                String description = "You are bad at "+name+" this week";
                int iconResId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_ICON_RES_ID));
                boolean selected;
                selected = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_SELECTED)) == 1;
                int progress = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HABIT_PROGRESS));
                Habit habit = new Habit(id, name, description, iconResId, selected,progress);
                if(id>4){
                    habitList.add(habit);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return habitList;
    }
    public void updateHabitSelection(int habitId, boolean selected) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HABIT_SELECTED, selected ? 1 : 0);

        String whereClause = KEY_ID + " = ?";
        String[] whereArgs = {String.valueOf(habitId)};

        db.update(TABLE_HABIT, values, whereClause, whereArgs);
    }

    private void insertPreAddedHabits(SQLiteDatabase db) {
        //inserted some habits as dummy habits

        ContentValues habit1Values = new ContentValues();
        habit1Values.put(KEY_HABIT_NAME, "Cycling");
        habit1Values.put(KEY_HABIT_DESC, "Ride a bicycle for 30 minutes");
        habit1Values.put(KEY_HABIT_ICON_RES_ID, R.drawable.baseline_directions_bike_24);
        habit1Values.put(KEY_HABIT_SELECTED, 0);
        habit1Values.put(KEY_HABIT_PROGRESS,70);
        db.insert(TABLE_HABIT, null, habit1Values);

        ContentValues habit2Values = new ContentValues();
        habit2Values.put(KEY_HABIT_NAME, "Walking");
        habit2Values.put(KEY_HABIT_DESC, "Walk for 60 minutes");
        habit2Values.put(KEY_HABIT_ICON_RES_ID, R.drawable.baseline_directions_walk_24);
        habit2Values.put(KEY_HABIT_SELECTED, 0);
        habit2Values.put(KEY_HABIT_PROGRESS,80);
        db.insert(TABLE_HABIT, null, habit2Values);

        ContentValues habit3Values = new ContentValues();
        habit3Values.put(KEY_HABIT_NAME, "Reading");
        habit3Values.put(KEY_HABIT_DESC, "Read at least 10 pages");
        habit3Values.put(KEY_HABIT_ICON_RES_ID, R.drawable.baseline_menu_book_24);
        habit3Values.put(KEY_HABIT_SELECTED, 0);
        habit3Values.put(KEY_HABIT_PROGRESS,90);
        db.insert(TABLE_HABIT, null, habit3Values);

        ContentValues habit4Values = new ContentValues();
        habit4Values.put(KEY_HABIT_NAME, "Coding");
        habit4Values.put(KEY_HABIT_DESC, "Solve at least 1 problem this day");
        habit4Values.put(KEY_HABIT_ICON_RES_ID, R.drawable.baseline_computer_24);
        habit4Values.put(KEY_HABIT_SELECTED, 0);
        habit4Values.put(KEY_HABIT_PROGRESS,65);
        db.insert(TABLE_HABIT, null, habit4Values);

        ContentValues habit5Values = new ContentValues();
        habit5Values.put(KEY_HABIT_NAME, "Exercising");
        habit5Values.put(KEY_HABIT_DESC, "Exercise for 1 hour.\nIt is good for your health");
        habit5Values.put(KEY_HABIT_ICON_RES_ID, R.drawable.baseline_exercise_24);
        habit5Values.put(KEY_HABIT_SELECTED, 0);
        habit5Values.put(KEY_HABIT_PROGRESS,25);
        db.insert(TABLE_HABIT, null, habit5Values);

        ContentValues habit6Values = new ContentValues();
        habit6Values.put(KEY_HABIT_NAME, "Drink Water");
        habit6Values.put(KEY_HABIT_DESC, "Drink 4 Bottles of Water");
        habit6Values.put(KEY_HABIT_ICON_RES_ID, R.drawable.baseline_local_drink_24);
        habit6Values.put(KEY_HABIT_SELECTED, 0);
        habit6Values.put(KEY_HABIT_PROGRESS,10);
        db.insert(TABLE_HABIT, null, habit6Values);

        ContentValues habit7Values = new ContentValues();
        habit7Values.put(KEY_HABIT_NAME, "Sleep Well");
        habit7Values.put(KEY_HABIT_DESC, "Sleep for 8 hours a day");
        habit7Values.put(KEY_HABIT_ICON_RES_ID, R.drawable.baseline_bedtime_24);
        habit7Values.put(KEY_HABIT_SELECTED, 0);
        habit7Values.put(KEY_HABIT_PROGRESS,20);
        db.insert(TABLE_HABIT, null, habit7Values);


    }


}