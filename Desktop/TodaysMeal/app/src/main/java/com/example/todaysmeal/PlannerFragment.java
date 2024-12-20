package com.example.todaysmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todaysmeal.database.AppDatabase;
import com.example.todaysmeal.model.MealPlan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PlannerFragment extends Fragment {

    private String selectedDate;
    private AppDatabase database;

    private EditText inputBreakfast, inputLunch, inputDinner, inputSnack;
    private Button btnSaveMeal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planner, container, false);

        // Initialize views
        CalendarView calendarView = view.findViewById(R.id.calendar_view);
        inputBreakfast = view.findViewById(R.id.input_breakfast);
        inputLunch = view.findViewById(R.id.input_lunch);
        inputDinner = view.findViewById(R.id.input_dinner);
        inputSnack = view.findViewById(R.id.input_snack);
        btnSaveMeal = view.findViewById(R.id.btn_save_meal);

        // Initialize database
        database = AppDatabase.getInstance(requireContext());

        // Set the current date as default
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Update selected date on CalendarView selection
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            loadMealPlan(selectedDate);
        });

        // Save button click listener
        btnSaveMeal.setOnClickListener(v -> saveMealPlan());

        return view;
    }

    private void saveMealPlan() {
        String breakfast = inputBreakfast.getText().toString();
        String lunch = inputLunch.getText().toString();
        String dinner = inputDinner.getText().toString();
        String snack = inputSnack.getText().toString();

        // Create a new MealPlan object
        MealPlan mealPlan = new MealPlan();
        mealPlan.setDate(selectedDate);
        mealPlan.setBreakfast(breakfast);
        mealPlan.setLunch(lunch);
        mealPlan.setDinner(dinner);
        mealPlan.setSnack(snack);

        // Save to database in a background thread
        new Thread(() -> {
            database.mealPlanDao().insertMealPlan(mealPlan);
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Meal plan saved!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    private void loadMealPlan(String date) {
        // Load from database in a background thread
        new Thread(() -> {
            MealPlan mealPlan = database.mealPlanDao().getMealPlanByDate(date);
            requireActivity().runOnUiThread(() -> {
                if (mealPlan != null) {
                    inputBreakfast.setText(mealPlan.getBreakfast());
                    inputLunch.setText(mealPlan.getLunch());
                    inputDinner.setText(mealPlan.getDinner());
                    inputSnack.setText(mealPlan.getSnack());
                } else {
                    // Clear fields if no meal plan exists
                    inputBreakfast.setText("");
                    inputLunch.setText("");
                    inputDinner.setText("");
                    inputSnack.setText("");
                }
            });
        }).start();
    }
}
