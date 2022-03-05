package com.example.foodandbeverage.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodandbeverage.Adapter.MainAdapter;
import com.example.foodandbeverage.Categories.Food.ChickenCategory;
import com.example.foodandbeverage.R;
import com.example.foodandbeverage.RecipeData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MainAdapter mainAdapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //Food
        ArrayList<RecipeData> foodRecipeList = new ArrayList<>();
        foodRecipeList.add(new RecipeData("Chicken", R.drawable.chicken));
        foodRecipeList.add(new RecipeData("Beef", R.drawable.beef));
        foodRecipeList.add(new RecipeData("Lamb", R.drawable.lamb));
        foodRecipeList.add(new RecipeData("Seafood", R.drawable.seafood));
        foodRecipeList.add(new RecipeData("Pork", R.drawable.pork));
        foodRecipeList.add(new RecipeData("Vegetarian", R.drawable.vegetarian));

        //Beverages
        foodRecipeList.add(new RecipeData("Mocktails & Cocktails", R.drawable.mocktail));
        foodRecipeList.add(new RecipeData("Tea", R.drawable.tea));
        foodRecipeList.add(new RecipeData("Coffee", R.drawable.coffee));
        foodRecipeList.add(new RecipeData("Smoothie", R.drawable.smoothie));

        mRecyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mainAdapter = new MainAdapter(MainActivity.this, foodRecipeList);
        mRecyclerView.setAdapter(mainAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buttons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.signout_click:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "You have signed out successfully.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
