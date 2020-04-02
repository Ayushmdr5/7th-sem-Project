package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private EditText editTextReport;
    private Button buttonReport;
    private Button buttonLogout;
    private TextView textViewSeeReport;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome "+ user.getEmail());
        editTextReport = (EditText) findViewById(R.id.editTextReport);
        buttonReport = (Button) findViewById(R.id.buttonReport) ;
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        textViewSeeReport = (TextView) findViewById(R.id.seeReport) ;

        buttonReport.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }

    private void reportDriver(){
        final String numberPlate = editTextReport.getText().toString().trim();
        Toast.makeText(this, numberPlate + " Reported Successfully", Toast.LENGTH_SHORT).show();

        ref = FirebaseDatabase.getInstance().getReference().child("DriversInfo").child(numberPlate);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer reports = dataSnapshot.child("reportCount").getValue(Integer.class);
                reports = reports + 1;
                ref.child("reportCount").setValue(reports);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("DATABASE ERROR");
            }
        });

    }
//        Toast.makeText(this, numberPlate, Toast.LENGTH_SHORT).show();

    @Override
    public void onClick(View view) {
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(view == buttonReport){
            reportDriver();
        }
    }
}

