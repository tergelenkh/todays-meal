package com.example.todaysmeal.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.todaysmeal.dao.MealPlanDao;
import com.example.todaysmeal.model.MealPlan;

@Database(entities = {MealPlan.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Singleton instance
    private static AppDatabase instance;

    // Abstract method for DAO
    public abstract MealPlanDao mealPlanDao();

    // Get database instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "mealplanner_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
