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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    String stateId,stateName,stateCode;


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
                    etStateName.setError("Please enter State Name");
                } else if (stateCode.isEmpty()) {
                    etStateCode.setError("Please enter State code");
                } else {

                    StateData stateData = new StateData(stateId, stateName, stateCode);
                    myref.child(stateId).setValue(stateData);

                    Toast.makeText(AddStateActivity.this, "Data inserted Successfully !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddStateActivity.this, RegistrationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });




    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
