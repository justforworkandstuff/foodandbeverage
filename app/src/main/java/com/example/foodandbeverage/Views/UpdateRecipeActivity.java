package com.example.foodandbeverage.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodandbeverage.R;
import com.example.foodandbeverage.RecipeData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateRecipeActivity extends AppCompatActivity {

    ImageView recipeImage;
    Uri uri;
    EditText text_name, text_description, text_ingredients, text_steps;
    String imageUrl;
    String key, oldimageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String recipeName, recipeDescription, recipeCategory, recipeIngredients, recipeSteps;
    TextView text_category;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        recipeImage = findViewById(R.id.update_image);
        text_name = findViewById(R.id.update_name);
        text_description = findViewById(R.id.update_description);
        text_ingredients = findViewById(R.id.update_ingredients);
        text_steps = findViewById(R.id.update_steps);
        text_category = findViewById(R.id.update_category);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            Glide.with(UpdateRecipeActivity.this)
                    .load(bundle.getString("oldimageUrl"))
                    .into(recipeImage);

            text_name.setText(bundle.getString("recipeNameKey"));
            text_description.setText(bundle.getString("descriptionKey"));
            text_category.setText(bundle.getString("categoryKey"));
            text_ingredients.setText(bundle.getString("ingredientsKey"));
            text_steps.setText(bundle.getString("stepsKey"));
            key = bundle.getString("key");
            oldimageUrl = bundle.getString("oldimageUrl");

        }
    }

    public void button_select_image(View view)
    {
        Intent imagePicker = new Intent(Intent.ACTION_PICK);
        imagePicker.setType("image/*");
        startActivityForResult(imagePicker, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            uri = data.getData();
            recipeImage.setImageURI(uri);
            Toast.makeText(this, "You have selected an image.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "You have not select an image.", Toast.LENGTH_SHORT).show();
        }
    }

    public void button_update_image(View view)
    {
        recipeName = text_name.getText().toString().trim();
        recipeDescription = text_description.getText().toString().trim();
        recipeCategory = text_category.getText().toString().trim();
        recipeIngredients = text_ingredients.getText().toString().trim();
        recipeSteps = text_steps.getText().toString().trim();

        if(recipeName.length() == 0)
        {
            text_name.setError("Please enter the name of the recipe.");
        }
        else if(text_description.length() == 0)
        {
            text_description.setError("Please enter the description of the recipe.");
        }
        else if(text_ingredients.length() == 0)
        {
            text_ingredients.setError("Please enter the ingredients of the recipe.");
        }
        else if(text_steps.length() == 0)
        {
            text_steps.setError("Please enter the steps of this recipe.");
        }
        else if(uri == null)
        {
            Toast.makeText(UpdateRecipeActivity.this, "Please select a new image for the recipe.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Recipe is updating...");
            progressDialog.show();

            storageReference = FirebaseStorage.getInstance().getReference().child("RecipeImage").child(uri.getLastPathSegment());
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    uploadRecipe();
                    progressDialog.dismiss();

                    Toast.makeText(UpdateRecipeActivity.this, "Image uploaded successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateRecipeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    public void uploadRecipe()
    {
        RecipeData recipeData = new RecipeData(
                recipeName,
                recipeDescription,
                recipeCategory,
                recipeIngredients,
                recipeSteps,
                imageUrl
        );

        if(recipeCategory.equals("Chicken"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Chicken").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Beef"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Beef").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Lamb"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Lamb").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Seafood"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Seafood").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Pork"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Pork").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Vegetarian"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Vegetarian").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Mocktails & Cocktails"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Mocktails & Cocktails").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Tea"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Tea").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Coffee"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Coffee").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(recipeCategory.equals("Smoothie"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Smoothie").child(key);
            databaseReference.setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldimageUrl);
                    storageReferenceNew.delete();
                    Toast.makeText(UpdateRecipeActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
