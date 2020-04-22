package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.modals.DistrictData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class District extends AppCompatActivity {
    EditText edtstate,edtdistrict,edtdistrictcode;
    String state,districtname,districtcode;
    Button btnsubmit;
   // DatabaseHelper d2;
    DatabaseReference myref;
    protected void onCreate(Bundle savedinstance) {
        super.onCreate(savedinstance);
        setContentView(R.layout.district);
        //d2=new DatabaseHelper(this);

        myref = FirebaseDatabase.getInstance().getReference().child("District_Records");

        edtstate=findViewById(R.id.statename);
        edtdistrict=findViewById(R.id.districtname);
        //st_nm=findViewById(R.id.dstatec);
        edtdistrictcode=findViewById(R.id.districtcode);
        btnsubmit=findViewById(R.id.dregister);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               state=edtstate.getText().toString().trim();
                districtname=edtdistrict.getText().toString().trim();
                districtcode=edtdistrictcode.getText().toString().trim();

                if(state.isEmpty()) {
                    edtstate.setError("Please enter State name");
                } else if(districtname.isEmpty()) {
                    edtdistrict.setError("Please enter District Name");
                } else if(districtcode.isEmpty()) {
                    edtdistrictcode.setError("Please enter District Code");
                } else {

                   // DistrictData district = new DistrictData(state, districtname, districtcode);
                   // myref.push().setValue(district);

                    Toast.makeText(District.this, "Record Successfully Inserted !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(District.this, Activityselect.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                //sst_nm=st_nm.getText().toString();
                /*boolean res=d2.distins(sds_cd,sds_nm,sst_cd,sst_nm);
                if(res==false)
                {
                    Toast.makeText(v.getContext(),"not inserted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(v.getContext(),"inserted",Toast.LENGTH_SHORT).show();
                    Intent m1=new Intent(v.getContext(),Register.class);
                    startActivity(m1);
                }*/
            }
        });
    }
}