package com.example.fortaxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;


public class profileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button buttonReport;
    private TextView textViewSeeReport;
    private TextView textViewSeeReportCount;
    private EditText editTextNumberPlate;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome "+ user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        textViewSeeReport = (TextView) findViewById(R.id.seeReport) ;
        buttonLogout.setOnClickListener(this);

        editTextNumberPlate = (EditText) findViewById(R.id.numInfo);
        textViewSeeReportCount = (TextView) findViewById(R.id.seeReportCount);
        buttonReport = (Button) findViewById(R.id.buttonReport) ;
        buttonReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }
        if(view == buttonReport){

//            FirebaseDatabase.getInstance().getReference("name").setValue("hi");
//            Toast.makeText(this, "hiii", Toast.LENGTH_SHORT).show();
            //String user = FirebaseDatabase.getInstance().getReference().child("DriversInfo").getKey();

            String number = editTextNumberPlate.getText().toString().trim();
            ref = FirebaseDatabase.getInstance().getReference().child("DriversInfo").child(number);
           // ref = FirebaseDatabase.getInstance().getReference().child("DriversInfo").child("BB2222");
            //ref = FirebaseDatabase.getInstance().getReference().child("DriversInfo").orderByChild("email").child("BB2222");


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //int count = (int) dataSnapshot.child("reportCount").getValue();
                    //int intVal = (Integer) dataSnapshot.child("reportCount").getValue();
                    String name = dataSnapshot.child("name").getValue().toString();
                    Integer reports = dataSnapshot.child("reportCount").getValue(Integer.class);
                    textViewSeeReport.setText("Name: " + name);
                    textViewSeeReportCount.setText("Report Count:" + reports);
                    //ref.child("reportCount").setValue("5");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("DATABASE ERROR");
                }
            });
        }
    }
}
