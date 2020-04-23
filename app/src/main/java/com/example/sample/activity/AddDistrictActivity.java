package com.example.sample.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;
import com.example.sample.modals.DistrictData;
import com.example.sample.modals.StateData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDistrictActivity extends AppCompatActivity {

    @BindView(R.id.spinStateName)
    Spinner spinStateName;
    @BindView(R.id.spinStateCode)
    Spinner spinStateCode;
    @BindView(R.id.etDistName)
    TextInputEditText etDistName;
    @BindView(R.id.etDistCode)
    TextInputEditText etDistCode;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference myref,databaseReference;
    String distId,distName,distCode,stateCode,stateName;
    List<String> stateList,stateCodeList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_district);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add District");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
/*
        -Add Gender Radio Button
                -Add Spinners
                -Send Images to Firebase
                -Retrieve Image from Firebase as per Voter ID*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        myref = FirebaseDatabase.getInstance().getReference().child("District_Details");
        databaseReference=FirebaseDatabase.getInstance().getReference("State_Details");

        stateList = new ArrayList<String>();
        stateCodeList=new ArrayList<String>();

        // Retrieving State Name
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String stateName = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getState();
                        stateList.add(stateName);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddDistrictActivity.this,R.layout.support_simple_spinner_dropdown_item, stateList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinStateName.setAdapter(arrayAdapter);

                } else {
                    Toast.makeText(AddDistrictActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Retrieving State Code as per State Name
        spinStateName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateCodeList.clear();
                String selectedState = spinStateName.getSelectedItem().toString();

                Query query = databaseReference.orderByChild("state").equalTo(selectedState);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                String stateCode = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getStatecode();
                                stateCodeList.add(stateCode);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddDistrictActivity.this,R.layout.support_simple_spinner_dropdown_item, stateCodeList);
                            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinStateCode.setAdapter(arrayAdapter);
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(AddDistrictActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distName= Objects.requireNonNull(etDistName.getText()).toString().trim();
                distCode= Objects.requireNonNull(etDistCode.getText()).toString().trim();
                stateName=spinStateName.getSelectedItem().toString();
                stateCode=spinStateCode.getSelectedItem().toString();

                 if(stateName.isEmpty()) {
                    Toast.makeText(AddDistrictActivity.this, "Please choose State Name", Toast.LENGTH_SHORT).show();
                } else if(stateCode.isEmpty()) {
                    Toast.makeText(AddDistrictActivity.this, "Please choose State code", Toast.LENGTH_SHORT).show();
                } else if(distName.isEmpty()) {
                    etDistName.setError("Please enter District Name");
                } else if(distCode.isEmpty()) {
                    etDistCode.setError("Please enter District Code ");
                }else {
                    distId = myref.push().getKey();

                    DistrictData districtData = new DistrictData(stateName, stateCode, distId, distName, distCode);
                    myref.child(distId).setValue(districtData);

                    Toast.makeText(AddDistrictActivity.this, "Data inserted Successfully !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddDistrictActivity.this, RegistrationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
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

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
    }
}
