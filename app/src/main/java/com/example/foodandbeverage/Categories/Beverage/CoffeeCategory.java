package com.example.foodandbeverage.Categories.Beverage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.foodandbeverage.Adapter.RecipeAdapter;
import com.example.foodandbeverage.Categories.Food.ChickenCategory;
import com.example.foodandbeverage.R;
import com.example.foodandbeverage.RecipeData;
import com.example.foodandbeverage.Views.AddRecipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CoffeeCategory extends AppCompatActivity {

    RecyclerView coffeeView;
    List<RecipeData> coffeeRecipeList;
    ProgressDialog progressDialog;
    EditText search_Feature;
    RecipeAdapter coffeeAdapter;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_category);

        coffeeRecipeList = new ArrayList<>();

        //initialization
        search_Feature = findViewById(R.id.coffee_searchFeature);
        coffeeView = findViewById(R.id.coffee_recyclerView);

        //assigning view with layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CoffeeCategory.this, 1);
        coffeeView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading recipes..");

        coffeeAdapter = new RecipeAdapter(CoffeeCategory.this, coffeeRecipeList);
        coffeeView.setAdapter(coffeeAdapter);

        //connecting to database named "Coffee"-
        databaseReference = FirebaseDatabase.getInstance().getReference("Coffee");

        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coffeeRecipeList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren())
                {
                    RecipeData recipeData = itemSnapshot.getValue(RecipeData.class);
                    recipeData.setKey(itemSnapshot.getKey());
                    coffeeRecipeList.add(recipeData);
                }

                coffeeAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                progressDialog.dismiss();
            }
        });

        search_Feature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });
    }

    private void filter(String text)
    {
        ArrayList<RecipeData> filterList = new ArrayList<>();

        for(RecipeData item: coffeeRecipeList)
        {
            if(item.getRecipeName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getRecipeIngredients().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        coffeeAdapter.categoryFilteredList(filterList);
    }

    public void coffee_upload(View view)
    {
        startActivity(new Intent(this, AddRecipe.class));
    }
}
