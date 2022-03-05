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

import com.bumptech.glide.Glide;
import com.example.foodandbeverage.R;
import com.example.foodandbeverage.RecipeData;
import com.example.foodandbeverage.Views.RecipeDetails;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<IRecipeViewHolder>
{
    private Context mContext;
    private List<RecipeData> mRecipeList;
    private int lastPosition = -1;

    public RecipeAdapter(Context mContext, List<RecipeData> mRecipeList)
    {
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
    }

    @NonNull
    @Override
    public IRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_item, viewGroup, false);


        return new IRecipeViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final IRecipeViewHolder recipeViewHolder, int i) {

        Glide.with(mContext)
                .load(mRecipeList.get(i).getRecipeImage())
                .into(recipeViewHolder.mRecipeImage);

        //MainActivity data for each row
        recipeViewHolder.mRecipeName.setText(mRecipeList.get(i).getRecipeName());
        recipeViewHolder.mRecipeDescription.setText(("Description: " + mRecipeList.get(i).getRecipeDescription()));
        recipeViewHolder.mRecipeCategory.setText(("Category: " + mRecipeList.get(i).getCategory()));
        recipeViewHolder.mRecipeIngredients.setText(("Ingredients: " + mRecipeList.get(i).getRecipeIngredients()));
        recipeViewHolder.mRecipeSteps.setText(mRecipeList.get(i).getRecipeSteps());

        //RecipeDetails when clicked
        recipeViewHolder.mCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, RecipeDetails.class);
                intent.putExtra("Image", mRecipeList.get(recipeViewHolder.getAdapterPosition()).getRecipeImage());
                intent.putExtra("Recipe Name", mRecipeList.get(recipeViewHolder.getAdapterPosition()).getRecipeName());
                intent.putExtra("Description", mRecipeList.get(recipeViewHolder.getAdapterPosition()).getRecipeDescription());
                intent.putExtra("Ingredients", mRecipeList.get(recipeViewHolder.getAdapterPosition()).getRecipeIngredients());
                intent.putExtra("Steps", mRecipeList.get(recipeViewHolder.getAdapterPosition()).getRecipeSteps());
                intent.putExtra("Category", mRecipeList.get(recipeViewHolder.getAdapterPosition()).getCategory());
                intent.putExtra("keyValue", mRecipeList.get(recipeViewHolder.getAdapterPosition()).getKey());
                mContext.startActivity(intent);
            }
        });

        setAnimation(recipeViewHolder.itemView, i);
    }

    public void setAnimation(View viewtoAnimate, int position)
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
        return mRecipeList.size();
    }

    public void categoryFilteredList(ArrayList<RecipeData> filterList)
    {
        mRecipeList = filterList;
        notifyDataSetChanged();
    }
}

class IRecipeViewHolder extends RecyclerView.ViewHolder
{
    ImageView mRecipeImage;
    TextView mRecipeName, mRecipeDescription, mRecipeCategory, mRecipeIngredients, mRecipeSteps;
    CardView mCardView;

    public IRecipeViewHolder(View itemview)
    {
        super(itemview);

        mRecipeImage = itemview.findViewById(R.id.recycler_Image);
        mRecipeName = itemview.findViewById(R.id.recycler_recipeName);
        mRecipeDescription = itemview.findViewById(R.id.recycler_Description);
        mRecipeCategory = itemview.findViewById(R.id.recycler_Category);
        mRecipeIngredients = itemview.findViewById(R.id.recycler_Ingredients);
        mRecipeSteps = itemview.findViewById(R.id.recycler_Steps);
        mCardView = itemview.findViewById(R.id.recycler_cardView);
    }
}