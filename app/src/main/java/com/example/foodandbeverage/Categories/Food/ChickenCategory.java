package com.example.foodandbeverage.Categories.Food;

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
import android.widget.Toast;

import com.example.foodandbeverage.Adapter.RecipeAdapter;
import com.example.foodandbeverage.Views.AddRecipe;
import com.example.foodandbeverage.R;
import com.example.foodandbeverage.RecipeData;
import com.example.foodandbeverage.Views.LoginActivity;
import com.example.foodandbeverage.Views.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChickenCategory extends AppCompatActivity {

    RecyclerView chickenView;
    List<RecipeData> chickenRecipeList;
    ProgressDialog progressDialog;
    EditText search_Feature;
    RecipeAdapter chickenAdapter;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_category);

        //initialization
        chickenRecipeList = new ArrayList<>();
        search_Feature = findViewById(R.id.chicken_searchFeature);
        chickenView = findViewById(R.id.chicken_recyclerView);

        //assigning view with layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ChickenCategory.this, 1);
        chickenView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading recipes..");

        chickenAdapter = new RecipeAdapter(ChickenCategory.this, chickenRecipeList);
        chickenView.setAdapter(chickenAdapter);

        //connecting to database named "Chicken"-
        databaseReference = FirebaseDatabase.getInstance().getReference("Chicken");

        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chickenRecipeList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren())
                {
                    RecipeData recipeData = itemSnapshot.getValue(RecipeData.class);
                    recipeData.setKey(itemSnapshot.getKey());
                    chickenRecipeList.add(recipeData);
                }

                chickenAdapter.notifyDataSetChanged();
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

        for(RecipeData item: chickenRecipeList)
        {
            if(item.getRecipeName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getRecipeIngredients().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        chickenAdapter.categoryFilteredList(filterList);
    }

    public void chicken_upload(View view)
    {
        startActivity(new Intent(this, AddRecipe.class));
    }
}
