package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //Start Profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //empty email
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            //stopping the function executing further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //empty Password
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            //start profile activity here
                            Toast.makeText(MainActivity.this, "Registerd Succesfully", Toast.LENGTH_SHORT).show();

                                finish();
                                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view){
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            //open login activity
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
