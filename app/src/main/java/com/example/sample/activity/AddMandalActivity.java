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
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMandalActivity extends AppCompatActivity {

    @BindView(R.id.spinStateName)
    Spinner spinStateName;
    @BindView(R.id.spinStateCode)
    Spinner spinStateCode;
    @BindView(R.id.spinDistName)
    Spinner spinDistName;
    @BindView(R.id.spinDistCode)
    Spinner spinDistCode;
    @BindView(R.id.etMandalName)
    TextInputEditText etMandalName;
    @BindView(R.id.etMandalCode)
    TextInputEditText etMandalCode;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference myref,databaseReference,databaseReference1;
    String mandalName,mandalCode,mandalId,stateName,stateCode,districtName,districtCode;
    ArrayList<String> stateList,stateCodeList,districtList,districtCodeList;
    ProgressDialog progressDialog;

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
        stateCodeList = new ArrayList<String>();
        districtList = new ArrayList<>();
        districtCodeList = new ArrayList<>();

        myref = FirebaseDatabase.getInstance().getReference().child("Mandal_Details");
        databaseReference = FirebaseDatabase.getInstance().getReference("State_Details");
        databaseReference1=FirebaseDatabase.getInstance().getReference("District_Details");

        // Retrieving State Name
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String stateName = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getState();
                        stateList.add(stateName);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMandalActivity.this,R.layout.support_simple_spinner_dropdown_item, stateList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinStateName.setAdapter(arrayAdapter);

                } else {
                    Toast.makeText(AddMandalActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
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
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMandalActivity.this,R.layout.support_simple_spinner_dropdown_item, stateCodeList);
                            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinStateCode.setAdapter(arrayAdapter);
                        }
                        else{
                            Toast.makeText(AddMandalActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Retrieving District Names based on State Selected
                Query query1 = databaseReference1.orderByChild("state").equalTo(selectedState);
                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            districtList.clear();
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                String districtName = Objects.requireNonNull(dataSnapshot1.getValue(DistrictData.class)).getDistrictname();
                                districtList.add(districtName);
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMandalActivity.this,R.layout.support_simple_spinner_dropdown_item, districtList);
                            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinDistName.setAdapter(arrayAdapter);

                        } else {
                            districtList.clear();
                            districtCodeList.clear();
                            Toast.makeText(AddMandalActivity.this, "No data Found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Retrieving District Code as per District Name
                spinDistName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        districtCodeList.clear();
                        String selectedDistrict = spinDistName.getSelectedItem().toString();

                        Query query = databaseReference1.orderByChild("districtname").equalTo(selectedDistrict);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                        String districtCode = Objects.requireNonNull(dataSnapshot1.getValue(DistrictData.class)).getDistrictcode();
                                        districtCodeList.add(districtCode);
                                    }
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMandalActivity.this,R.layout.support_simple_spinner_dropdown_item, districtCodeList);
                                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                    spinDistCode.setAdapter(arrayAdapter);
                                    progressDialog.dismiss();
                                }
                                else{

                                    Toast.makeText(AddMandalActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandalId=myref.push().getKey();
                mandalCode= Objects.requireNonNull(etMandalCode.getText()).toString().trim();
                mandalName= Objects.requireNonNull(etMandalName.getText()).toString().trim();
                stateName = spinStateName.getSelectedItem().toString();
                stateCode = spinStateCode.getSelectedItem().toString();
                districtCode=spinDistCode.getSelectedItem().toString();
                districtName=spinDistName.getSelectedItem().toString();

                MandalData mandalData = new MandalData(stateName,stateCode,districtName,districtCode,mandalId,mandalName,mandalCode);
                myref.child(mandalId).setValue(mandalData);

                Toast.makeText(AddMandalActivity.this, "Data inserted Successfully !", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddMandalActivity.this,RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
