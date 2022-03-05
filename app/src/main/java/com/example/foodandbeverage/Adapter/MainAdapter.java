package com.example.foodandbeverage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodandbeverage.Categories.Beverage.CocktailsandMocktails;
import com.example.foodandbeverage.Categories.Beverage.CoffeeCategory;
import com.example.foodandbeverage.Categories.Beverage.SmoothieCategory;
import com.example.foodandbeverage.Categories.Beverage.TeaCategory;
import com.example.foodandbeverage.Categories.Food.BeefCategory;
import com.example.foodandbeverage.Categories.Food.LambCategory;
import com.example.foodandbeverage.Categories.Food.PorkCategory;
import com.example.foodandbeverage.Categories.Food.SeafoodCategory;
import com.example.foodandbeverage.Categories.Food.VegetarianCategory;
import com.example.foodandbeverage.Views.AddRecipe;
import com.example.foodandbeverage.Categories.Food.ChickenCategory;
import com.example.foodandbeverage.R;
import com.example.foodandbeverage.RecipeData;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder>
{
    private Context mContext;
    private ArrayList<RecipeData> myRecipeList;
    private int lastPosition = -1;

    public MainAdapter(Context mContext2, ArrayList<RecipeData> myRecipeList2)
    {
        this.mContext = mContext2;
        this.myRecipeList = myRecipeList2;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_recycler, viewGroup, false);

        return new MainViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder testViewHolder, int i) {

        RecipeData testItem = myRecipeList.get(i);
        //MainActivity data for each row
        testViewHolder.mCategoryImage.setImageResource(testItem.imageMethod());
        testViewHolder.mCategoryName.setText(testItem.getRecipeName());

        testViewHolder.mCategoryImage.setImageResource(testItem.imageMethod());
        testViewHolder.mCategoryName.setText(testItem.getRecipeName());

        //Go into category main activity
        testViewHolder.mCategoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(testViewHolder.mCategoryName.getText().equals("Chicken"))
                {
                    Intent intent = new Intent(mContext, ChickenCategory.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Beef"))
                {
                    Intent intent = new Intent(mContext, BeefCategory.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Lamb"))
                {
                    Intent intent = new Intent(mContext, LambCategory.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Seafood"))
                {
                    Intent intent = new Intent(mContext, SeafoodCategory.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Pork"))
                {
                    Intent intent = new Intent(mContext, PorkCategory.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Vegetarian"))
                {
                    Intent intent = new Intent(mContext, VegetarianCategory.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Mocktails & Cocktails"))
                {
                    Intent intent = new Intent(mContext, CocktailsandMocktails.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Tea"))
                {
                    Intent intent = new Intent(mContext, TeaCategory.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Coffee"))
                {
                    Intent intent = new Intent(mContext, CoffeeCategory.class);
                    mContext.startActivity(intent);
                }
                else if(testViewHolder.mCategoryName.getText().equals("Smoothie"))
                {
                    Intent intent = new Intent(mContext, SmoothieCategory.class);
                    mContext.startActivity(intent);
                }
            }
        });
        setAnimation2(testViewHolder.itemView, i);
    }

    public void setAnimation2(View viewtoAnimate, int position)
    {
        if(position > lastPosition)
        {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(1500);
            viewtoAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount()
    {
        return myRecipeList.size();
    }

    public void mainFilterList(ArrayList<RecipeData> mainFilterList)
    {
        myRecipeList = mainFilterList;
        notifyDataSetChanged();
    }
}

class MainViewHolder extends RecyclerView.ViewHolder
{
    ImageView mCategoryImage;
    TextView mCategoryName;
    CardView mCategoryCardView;

    public MainViewHolder(View itemview)
    {
        super(itemview);

        mCategoryImage = itemview.findViewById(R.id.main_category_image);
        mCategoryName = itemview.findViewById(R.id.main_category_name);
        mCategoryCardView = itemview.findViewById(R.id.main_cardView);
    }
}
