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

public class SmoothieCategory extends AppCompatActivity {

    RecyclerView smoothieView;
    List<RecipeData> smoothieRecipeList;
    ProgressDialog progressDialog;
    EditText search_Feature;
    RecipeAdapter smoothieAdapter;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoothie_category);

        smoothieRecipeList = new ArrayList<>();

        //initialization
        search_Feature = findViewById(R.id.smoothie_searchFeature);
        smoothieView = findViewById(R.id.smoothie_recyclerView);

        //assigning view with layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SmoothieCategory.this, 1);
        smoothieView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading recipes..");

        smoothieAdapter = new RecipeAdapter(SmoothieCategory.this, smoothieRecipeList);
        smoothieView.setAdapter(smoothieAdapter);

        //connecting to database named "Smoothie"-
        databaseReference = FirebaseDatabase.getInstance().getReference("Smoothie");

        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                smoothieRecipeList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren())
                {
                    RecipeData recipeData = itemSnapshot.getValue(RecipeData.class);
                    recipeData.setKey(itemSnapshot.getKey());
                    smoothieRecipeList.add(recipeData);
                }

                smoothieAdapter.notifyDataSetChanged();
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

        for(RecipeData item: smoothieRecipeList)
        {
            if(item.getRecipeName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getRecipeIngredients().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        smoothieAdapter.categoryFilteredList(filterList);
    }

    public void smoothie_upload(View view)
    {
        startActivity(new Intent(this, AddRecipe.class));
    }
}
