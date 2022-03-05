package com.example.foodandbeverage.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodandbeverage.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RecipeDetails extends AppCompatActivity {

    TextView mRecipeName;
    TextView mRecipeDescription;
    TextView mRecipeCategory;
    TextView mRecipeIngredients;
    TextView mRecipeSteps;
    ImageView mRecipeImage;
    String key = "";
    String imageUrl = "";
    Button updating_button, deleting_button;
    private DatabaseReference reference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        mRecipeName = findViewById(R.id.details_name);
        mRecipeDescription = findViewById(R.id.details_description);
        mRecipeIngredients = findViewById(R.id.details_ingredients);
        mRecipeSteps = findViewById(R.id.details_steps);
        mRecipeCategory = findViewById(R.id.details_category);
        mRecipeImage = findViewById(R.id.details_image);
        updating_button = findViewById(R.id.update_button);
        deleting_button = findViewById(R.id.delete_button);

        Bundle mBundle = getIntent().getExtras();
        if(mBundle!= null) {
            mRecipeName.setText(mBundle.getString("Recipe Name"));
            mRecipeDescription.setText(mBundle.getString("Description"));
            mRecipeCategory.setText(mBundle.getString("Category"));
            mRecipeIngredients.setText(mBundle.getString("Ingredients"));
            mRecipeSteps.setText(mBundle.getString("Steps"));
            key = mBundle.getString("keyValue");
            imageUrl = mBundle.getString("Image");

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(mRecipeImage);

        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getEmail().equals("feiimomoo@gmail.com"))
        {
            updating_button.setEnabled(true);
            deleting_button.setEnabled(true);
            Toast.makeText(RecipeDetails.this, "Management of recipe is enabled for this account.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(RecipeDetails.this, "You're not allowed to edit and delete the recipe with this account.", Toast.LENGTH_SHORT).show();
        }
    }

    public void button_delete_recipe(View view)
    {
        if(mRecipeCategory.getText().toString().equals("Chicken"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Chicken");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Beef"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Beef");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Lamb"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Lamb");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Seafood"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Seafood");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Pork"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Pork");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Vegetarian"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Vegetarian");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Mocktails & Cocktails"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Mocktails & Cocktails");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Tea"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Tea");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Coffee"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Coffee");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mRecipeCategory.getText().equals("Smoothie"))
        {
            reference = FirebaseDatabase.getInstance().getReference("Smoothie");
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    reference.child(key).removeValue();
                    Toast.makeText(RecipeDetails.this, "Recipe deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetails.this, "Recipe deletion is unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void button_update_recipe(View view)
    {
        startActivity(new Intent(getApplicationContext(), UpdateRecipeActivity.class)
                .putExtra("recipeNameKey", mRecipeName.getText().toString())
                .putExtra("descriptionKey", mRecipeDescription.getText().toString())
                .putExtra("ingredientsKey", mRecipeIngredients.getText().toString())
                .putExtra("categoryKey", mRecipeCategory.getText().toString())
                .putExtra("stepsKey", mRecipeSteps.getText().toString())
                .putExtra("oldimageUrl", imageUrl)
                .putExtra("key", key)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.share_click:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");

                String recipeName = mRecipeName.getText().toString();
                String recipeDescription = mRecipeDescription.getText().toString();
                String recipeCategory = mRecipeCategory.getText().toString();
                String recipeIngredients = mRecipeIngredients.getText().toString();
                String recipeSteps = mRecipeSteps.getText().toString();

                String share_details = "Recipe Name:\n" + recipeName +
                        "\nRecipe Category:\n" + recipeCategory +
                        "\nRecipe Description:\n" + recipeDescription +
                        "\nRecipe Ingredients:\n" + recipeIngredients +
                        "\nRecipe Steps:\n" + recipeSteps;

                sharingIntent.putExtra(sharingIntent.EXTRA_SUBJECT, "I want to share this recipe with you!");
                sharingIntent.putExtra(sharingIntent.EXTRA_TEXT, share_details);

                startActivity(Intent.createChooser(sharingIntent,"Share using"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
