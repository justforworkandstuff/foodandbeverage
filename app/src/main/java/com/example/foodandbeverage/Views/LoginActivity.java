package com.example.foodandbeverage.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodandbeverage.Categories.Food.ChickenCategory;
import com.example.foodandbeverage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLogin, mRegister;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In..");
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null)
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Welcome back, " + user.getEmail().toString(), Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
            }
        };

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        mLogin = findViewById(R.id.login_button);
        mRegister = findViewById(R.id.register_button);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if(email.length() == 0)
                {
                    mEmail.setError("Please enter your email.");
                }
                else if(password.length() == 0)
                {
                    mPassword.setError("Please enter your password.");
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful())
                                    {
                                        Toast.makeText(LoginActivity.this, "Invalid email/Email has already been used. Please use another email to register again!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        String user_id = mAuth.getCurrentUser().getUid();
                                        DatabaseReference currentUser_db = FirebaseDatabase.getInstance().getReference()
                                                .child("User").child("CommonUser").child(user_id);
                                        currentUser_db.setValue(true);
                                    }
                                }
                            });
                }
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if(email.length() == 0)
                {
                    mEmail.setError("Please enter your email.");
                }
                if(password.length() == 0)
                {
                    mPassword.setError("Please enter your password.");
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, "The email or password is incorrect! Please try again!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
