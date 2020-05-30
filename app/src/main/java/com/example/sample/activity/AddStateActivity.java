package com.example.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sample.R;
import com.example.sample.modals.StateData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStateActivity extends AppCompatActivity {

    @BindView(R.id.etStateName)
    TextInputEditText etStateName;
    @BindView(R.id.etStateCode)
    TextInputEditText etStateCode;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference myref;
    String stateId, stateName, stateCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_state);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add State");


        myref = FirebaseDatabase.getInstance().getReference().child("State_Details");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateName = Objects.requireNonNull(etStateName.getText()).toString().trim();
                stateCode = Objects.requireNonNull(etStateCode.getText()).toString();
                stateId = myref.push().getKey();
                if (stateName.isEmpty()) {
                    Toast.makeText(AddStateActivity.this, "Enter State Name", Toast.LENGTH_SHORT).show();
                } else if (stateCode.isEmpty()) {
                    Toast.makeText(AddStateActivity.this, "Enter State Code", Toast.LENGTH_SHORT).show();
                } else {
                    myref.child(stateName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                //user exists, do something
                                Toast.makeText(AddStateActivity.this, "Already State Name Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                //user does not exist, do something else
                                StateData stateData = new StateData(stateId, stateName, stateCode);
                                myref.child(stateName).setValue(stateData);
                                etStateCode.setText("");
                                etStateName.setText("");
                                Toast.makeText(AddStateActivity.this, "Data inserted Successfully !", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
