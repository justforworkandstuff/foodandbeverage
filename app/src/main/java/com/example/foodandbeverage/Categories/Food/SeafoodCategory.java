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

import com.example.foodandbeverage.Adapter.RecipeAdapter;
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

public class SeafoodCategory extends AppCompatActivity {
    RecyclerView seafoodView;
    List<RecipeData> seafoodRecipeList;
    ProgressDialog progressDialog;
    EditText search_Feature;
    RecipeAdapter seafoodAdapter;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seafood_category);

        seafoodRecipeList = new ArrayList<>();

        //initialization
        search_Feature = findViewById(R.id.seafood_searchFeature);
        seafoodView = findViewById(R.id.seafood_recyclerView);

        //assigning view with layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SeafoodCategory.this, 1);
        seafoodView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading recipes..");

        seafoodAdapter = new RecipeAdapter(SeafoodCategory.this, seafoodRecipeList);
        seafoodView.setAdapter(seafoodAdapter);

        //connecting to database named "Seafood"-
        databaseReference = FirebaseDatabase.getInstance().getReference("Seafood");

        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                seafoodRecipeList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren())
                {
                    RecipeData recipeData = itemSnapshot.getValue(RecipeData.class);
                    recipeData.setKey(itemSnapshot.getKey());
                    seafoodRecipeList.add(recipeData);
                }

                seafoodAdapter.notifyDataSetChanged();
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

        for(RecipeData item: seafoodRecipeList)
        {
            if(item.getRecipeName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getRecipeIngredients().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        seafoodAdapter.categoryFilteredList(filterList);
    }

    public void seafood_upload(View view)
    {
        startActivity(new Intent(this, AddRecipe.class));
    }
}
