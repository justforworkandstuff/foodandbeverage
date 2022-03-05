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

public class PorkCategory extends AppCompatActivity {

    RecyclerView porkView;
    List<RecipeData> porkRecipeList;
    ProgressDialog progressDialog;
    EditText search_Feature;
    RecipeAdapter porkAdapter;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pork_category);

        porkRecipeList = new ArrayList<>();

        //initialization
        search_Feature = findViewById(R.id.pork_searchFeature);
        porkView = findViewById(R.id.pork_recyclerView);

        //assigning view with layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PorkCategory.this, 1);
        porkView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading recipes..");

        porkAdapter = new RecipeAdapter(PorkCategory.this, porkRecipeList);
        porkView.setAdapter(porkAdapter);

        //connecting to database named "Pork"-
        databaseReference = FirebaseDatabase.getInstance().getReference("Pork");

        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                porkRecipeList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren())
                {
                    RecipeData recipeData = itemSnapshot.getValue(RecipeData.class);
                    recipeData.setKey(itemSnapshot.getKey());
                    porkRecipeList.add(recipeData);
                }

                porkAdapter.notifyDataSetChanged();
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

        for(RecipeData item: porkRecipeList)
        {
            if(item.getRecipeName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getRecipeIngredients().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        porkAdapter.categoryFilteredList(filterList);
    }

    public void pork_upload(View view)
    {
        startActivity(new Intent(this, AddRecipe.class));
    }
}
