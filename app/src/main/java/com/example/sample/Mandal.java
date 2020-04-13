package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.modals.MandalData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Mandal extends AppCompatActivity {
    Button bt1;
    DatabaseHelper d3;
    EditText edtstate,edtstatecode,edtdistrictname,edtdistrictcode,edtmandal,edtmandalcode ;
    String state,statecode,district,districtcode,mandal,mandalcode;
    DatabaseReference myref;
    protected void onCreate(Bundle savedinstance) {
        super.onCreate(savedinstance);
        setContentView(R.layout.mandal);

        myref = FirebaseDatabase.getInstance().getReference().child("Mandal_Records");

        bt1=findViewById(R.id.mregister);
        d3=new DatabaseHelper(this);
        edtstate=findViewById(R.id.mstate);
        edtstatecode=findViewById(R.id.mstatecode);
        edtdistrictname=findViewById(R.id.mdistrictname);
        edtdistrictcode=findViewById(R.id.mdistrictcode);
        edtmandal=findViewById(R.id.mandalname);
        edtmandalcode=findViewById(R.id.mandalcode);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = edtstate.getText().toString();
                statecode= edtstatecode.getText().toString();
                district= edtdistrictname.getText().toString();
                districtcode= edtdistrictcode.getText().toString();
                mandal= edtmandal.getText().toString();
                mandalcode = edtmandalcode.getText().toString();

                if(state.isEmpty()) {
                    edtstate.setError("Please enter State name");
                } else if(statecode.isEmpty()) {
                    edtstatecode.setError("Please enter State Code");
                } else if(district.isEmpty()) {
                    edtdistrictname.setError("Please enter District Name");
                } else if(districtcode.isEmpty()) {
                    edtdistrictcode.setError("Please enter District Code");
                } else if(mandal.isEmpty()) {
                    edtmandal.setError("Please enter Mandal");
                } else if(mandalcode.isEmpty()) {
                    edtmandalcode.setError("Please enter Mandal Code");
                } else {

                    myref.push().setValue(new MandalData(state, statecode, district, districtcode, mandal, mandalcode));

                    Toast.makeText(Mandal.this, "Record Inserted Successfully !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Mandal.this, Activityselect.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


                /*boolean res = d3.mndlins(smnc, smnnm, sdstc, sdstnm, sstc, sstnm);
                if (res == false) {
                    Toast.makeText(v.getContext(), "not inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(),"inserted",Toast.LENGTH_SHORT).show();
                    Intent m1 = new Intent(v.getContext(), Register.class);
                    startActivity(m1);
                }*/
            }
        });


    }
}

