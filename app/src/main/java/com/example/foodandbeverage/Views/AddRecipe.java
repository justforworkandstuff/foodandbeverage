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
import android.widget.Toast;

import com.example.foodandbeverage.R;
import com.example.foodandbeverage.RecipeData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class AddRecipe extends AppCompatActivity {

    ImageView recipeImage;
    Uri uri;
    EditText text_name, text_description, text_ingredients, text_steps;
    String imageUrl;
    Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipeImage = findViewById(R.id.add_image);
        text_name = findViewById(R.id.add_name);
        text_description = findViewById(R.id.add_description);
        mySpinner = findViewById(R.id.add_menu);
        text_ingredients = findViewById(R.id.add_ingredients);
        text_steps = findViewById(R.id.add_steps);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddRecipe.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
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
            Toast.makeText(this, "You did not select an image.", Toast.LENGTH_SHORT).show();
        }
    }

    public void upload()
    {
        if(text_name.length() == 0)
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
            text_steps.setError("Please enter the steps for the recipe.");
        }
        else if(uri == null)
        {
            Toast.makeText(AddRecipe.this, "Please select an image for the recipe.", Toast.LENGTH_SHORT).show();
        }
        else if(uri != null)
        {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("RecipeImage").child(uri.getLastPathSegment());

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Recipe is uploading...");
            progressDialog.show();

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    uploadRecipe();
                    progressDialog.dismiss();

                    Toast.makeText(AddRecipe.this, "Image uploaded successfully.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    public void button_upload_image(View view)
    {
        upload();
    }

    public void uploadRecipe()
    {
        RecipeData recipeData = new RecipeData(
                text_name.getText().toString(),
                text_description.getText().toString(),
                mySpinner.getSelectedItem().toString(),
                text_ingredients.getText().toString(),
                text_steps.getText().toString(),
                imageUrl
        );

        String currentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        if(mySpinner.getSelectedItem().toString().contains("Chicken"))
        {
            FirebaseDatabase.getInstance().getReference("Chicken").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mySpinner.getSelectedItem().toString().contains("Beef"))
        {
            FirebaseDatabase.getInstance().getReference("Beef").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mySpinner.getSelectedItem().toString().contains("Lamb"))
        {
            FirebaseDatabase.getInstance().getReference("Lamb").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mySpinner.getSelectedItem().toString().contains("Seafood"))
        {
            FirebaseDatabase.getInstance().getReference("Seafood").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mySpinner.getSelectedItem().toString().contains("Pork"))
        {
            FirebaseDatabase.getInstance().getReference("Pork").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }

        else if(mySpinner.getSelectedItem().toString().contains("Vegetarian"))
        {
            FirebaseDatabase.getInstance().getReference("Vegetarian").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mySpinner.getSelectedItem().toString().contains("Mocktails & Cocktails"))
        {
            FirebaseDatabase.getInstance().getReference("Mocktails & Cocktails").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mySpinner.getSelectedItem().toString().contains("Tea"))
        {
            FirebaseDatabase.getInstance().getReference("Tea").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mySpinner.getSelectedItem().toString().contains("Coffee"))
        {
            FirebaseDatabase.getInstance().getReference("Coffee").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mySpinner.getSelectedItem().toString().contains("Smoothie"))
        {
            FirebaseDatabase.getInstance().getReference("Smoothie").child(currentDateTime).setValue(recipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddRecipe.this, "Recipe has been uploaded successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddRecipe.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
