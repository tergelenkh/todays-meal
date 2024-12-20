package com.example.todaysmeal.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todaysmeal.model.MealPlan;

import java.util.List;

@Dao
public interface MealPlanDao {

    // Insert a new meal plan
    @Insert
    void insertMealPlan(MealPlan mealPlan);

    // Get a meal plan by date
    @Query("SELECT * FROM meal_plans WHERE date = :date")
    MealPlan getMealPlanByDate(String date);

    // Get all meal plans (e.g., for a week)
    @Query("SELECT * FROM meal_plans")
    List<MealPlan> getAllMealPlans();

    // Delete a meal plan by ID
    @Query("DELETE FROM meal_plans WHERE id = :id")
    void deleteMealPlanById(int id);
}
