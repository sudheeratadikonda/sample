package com.example.sample.activity;

import android.app.ProgressDialog;
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

public class AddDistrictActivity extends AppCompatActivity {

    @BindView(R.id.spinStateName)
    Spinner spinStateName;
    /*@BindView(R.id.spinStateCode)
    Spinner spinStateCode;*/
    @BindView(R.id.etDistName)
    TextInputEditText etDistName;
    @BindView(R.id.etDistCode)
    TextInputEditText etDistCode;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference myref, databaseReference;
    String distId, distName, distCode, stateCode, stateName;
    List<String> stateList, stateCodeList;
    ProgressDialog progressDialog;
    @BindView(R.id.etStateCode)
    TextInputEditText etStateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_district);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add District");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        myref = FirebaseDatabase.getInstance().getReference().child("District_Details");
        databaseReference = FirebaseDatabase.getInstance().getReference("State_Details");

        stateList = new ArrayList<String>();
        stateList.add("Select State Name");
        stateCodeList = new ArrayList<String>();
        stateCodeList.add("Select State Code");

        // Retrieving State Name
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    stateCodeList.add("Select State Code");
                    stateList.clear();
                    stateList.add("Select State Name");

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String stateName = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getState();
                        stateList.add(stateName);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddDistrictActivity.this, R.layout.support_simple_spinner_dropdown_item, stateList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinStateName.setAdapter(arrayAdapter);

                    progressDialog.dismiss();
                    //Retrieving State Code as per State Name

                    spinStateName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedState = spinStateName.getSelectedItem().toString();

                            Query query = databaseReference.orderByChild("state").equalTo(selectedState);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        stateCodeList.clear();
                                        stateCodeList.add("Select State Code");
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            String stateCode = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getStatecode();

                                            etStateCode.setText(stateCode);
                                            // stateCodeList.add(stateCode);
                                        }
                                       /* ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddDistrictActivity.this, R.layout.support_simple_spinner_dropdown_item, stateCodeList);
                                        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                        spinStateCode.setAdapter(arrayAdapter);*/
                                        progressDialog.dismiss();
                                    } else {
                                        stateCodeList.clear();
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

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distId = myref.push().getKey();
                distName = Objects.requireNonNull(etDistName.getText()).toString().trim();
                distCode = Objects.requireNonNull(etDistCode.getText()).toString().trim();
                stateName = spinStateName.getSelectedItem().toString();
                stateCode = Objects.requireNonNull(etStateCode.getText()).toString();

                if (stateName.isEmpty()) {
                    Toast.makeText(AddDistrictActivity.this, "Please Select State Name", Toast.LENGTH_SHORT).show();
                } else if (stateCode.isEmpty()) {
                    Toast.makeText(AddDistrictActivity.this, "Please Select State code", Toast.LENGTH_SHORT).show();
                } else if (distName.isEmpty()) {
                    Toast.makeText(AddDistrictActivity.this, "Please enter District Name", Toast.LENGTH_SHORT).show();
                } else if (distCode.isEmpty()) {
                    Toast.makeText(AddDistrictActivity.this, "Please enter District Code ", Toast.LENGTH_SHORT).show();
                } else {

                    myref.child(distName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                //user exists, do something
                                Toast.makeText(AddDistrictActivity.this, "Already District Name Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                DistrictData districtData = new DistrictData(stateName, stateCode, distId, distName, distCode);
                                myref.child(distName).setValue(districtData);
                                spinStateName.setSelection(0);
                               // spinStateCode.setSelection(0);
                                etStateCode.setText("");
                                etDistName.setText("");
                                etDistCode.setText("");

                                Toast.makeText(AddDistrictActivity.this, "Data inserted Successfully !", Toast.LENGTH_SHORT).show();

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
