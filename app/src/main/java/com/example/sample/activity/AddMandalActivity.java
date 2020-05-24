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
import com.example.sample.modals.MandalData;
import com.example.sample.modals.StateData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMandalActivity extends AppCompatActivity {

    @BindView(R.id.spinStateName)
    Spinner spinStateName;
    @BindView(R.id.spinDistName)
    Spinner spinDistName;
    @BindView(R.id.etMandalName)
    TextInputEditText etMandalName;
    @BindView(R.id.etMandalCode)
    TextInputEditText etMandalCode;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference myref, databaseReference, databaseReference1;
    String mandalName, mandalCode, mandalId, stateName, stateCode, districtName, districtCode;
    ArrayList<String> stateList, stateCodeList, districtList, districtCodeList;
    ProgressDialog progressDialog;
    @BindView(R.id.etStateCode)
    TextInputEditText etStateCode;
    @BindView(R.id.etDistCode)
    TextInputEditText etDistCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mandal);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Mandal");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        stateList = new ArrayList<String>();
        stateList.add("Select State Name");
        districtList = new ArrayList<>();
        districtList.add("Select District Name");


        myref = FirebaseDatabase.getInstance().getReference().child("Mandal_Details");
        databaseReference = FirebaseDatabase.getInstance().getReference("State_Details");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("District_Details");

getStates();
getDistrict("");


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandalId = myref.push().getKey();
                mandalCode = Objects.requireNonNull(etMandalCode.getText()).toString().trim();
                mandalName = Objects.requireNonNull(etMandalName.getText()).toString().trim();
                stateName = spinStateName.getSelectedItem().toString();
                stateCode = Objects.requireNonNull(etStateCode.getText()).toString();
                districtCode = Objects.requireNonNull(etDistCode.getText()).toString();
                districtName = spinDistName.getSelectedItem().toString();

                if (stateName.isEmpty()) {
                    Toast.makeText(AddMandalActivity.this, "Please choose State Name", Toast.LENGTH_SHORT).show();
                } else if (stateCode.isEmpty()) {
                    Toast.makeText(AddMandalActivity.this, "Please choose State code", Toast.LENGTH_SHORT).show();
                } else if (districtName.isEmpty()) {
                    Toast.makeText(AddMandalActivity.this, "Please choose District Name", Toast.LENGTH_SHORT).show();
                } else if (districtCode.isEmpty()) {
                    Toast.makeText(AddMandalActivity.this, "Please choose District Code", Toast.LENGTH_SHORT).show();
                } else if (mandalName.isEmpty()) {

                    Toast.makeText(AddMandalActivity.this, "Please enter Mandal Name", Toast.LENGTH_SHORT).show();
                } else if (mandalCode.isEmpty()) {

                    Toast.makeText(AddMandalActivity.this, "Please enter Mandal Code", Toast.LENGTH_SHORT).show();
                } else {

                    myref.child(mandalName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                //user exists, do something
                                Toast.makeText(AddMandalActivity.this, "Already Mandal Name Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                MandalData mandalData = new MandalData(stateName, stateCode, districtName, districtCode, mandalId, mandalName, mandalCode);
                                myref.child(mandalName).setValue(mandalData);

                                Toast.makeText(AddMandalActivity.this, "Data inserted Successfully !", Toast.LENGTH_SHORT).show();

                                spinStateName.setSelection(0);
                                etDistCode.setText("");
                                spinDistName.setSelection(0);
                                etStateCode.setText("");
                                etMandalName.setText("");
                                etMandalCode.setText("");


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
    public void getStates() {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    stateList.clear();
                    stateList.add("Select State");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String stateName = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getState();
                        stateList.add(stateName);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMandalActivity.this, R.layout.support_simple_spinner_dropdown_item, stateList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinStateName.setAdapter(arrayAdapter);


                    spinStateName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            stateName = spinStateName.getSelectedItem().toString();
                            Query query = databaseReference.orderByChild("state").equalTo(stateName);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            String stateCode = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getStatecode();
                                            etStateCode.setText(stateCode);
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            getDistrict(stateName);


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                            getDistrict("");
                        }
                    });

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    stateList.clear();
                    stateList.add("Select State Name");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDistrict(String selectedState) {


        //Retrieving District Names based on State Selected
        Query query1 = databaseReference1.orderByChild("state").equalTo(selectedState);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    districtList.clear();
                    districtList.add("Select District");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String districtName = Objects.requireNonNull(dataSnapshot1.getValue(DistrictData.class)).getDistrictname();
                        districtList.add(districtName);
                    }

                } else {
                    districtList.clear();
                    districtList.add("Select District");
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMandalActivity.this, R.layout.support_simple_spinner_dropdown_item, districtList);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinDistName.setAdapter(arrayAdapter);

                //Retrieving Mandal Name as per District Name
                spinDistName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        districtName = spinDistName.getSelectedItem().toString();
                        Query query = databaseReference1.orderByChild("districtname").equalTo(districtName);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        String districtCode = Objects.requireNonNull(dataSnapshot1.getValue(DistrictData.class)).getDistrictcode();
                                        etDistCode.setText(districtCode);
                                    }
                                    progressDialog.dismiss();
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
