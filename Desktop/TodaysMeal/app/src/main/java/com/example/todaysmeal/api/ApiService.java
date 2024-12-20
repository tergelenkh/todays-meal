package com.example.todaysmeal.api;

import com.example.todaysmeal.model.MealPlan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    // Endpoint to generate a grocery list
    @POST("/generate-grocery")
    Call<List<String>> getGroceryList(@Body List<MealPlan> mealPlans);
}
