package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class District extends AppCompatActivity {
    EditText ds_nm,ds_cd,st_nm,st_cd;
    String sds_nm,sds_cd,sst_nm,sst_cd;
    Button bt1;
    DatabaseHelper d2;
    protected void onCreate(Bundle savedinstance) {
        super.onCreate(savedinstance);
        setContentView(R.layout.district);
        d2=new DatabaseHelper(this);
        ds_nm=findViewById(R.id.ddistrict);
        ds_cd=findViewById(R.id.districtc);
        //st_nm=findViewById(R.id.dstatec);
        st_cd=findViewById(R.id.dstatec);
        bt1=findViewById(R.id.dregister);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sds_cd=ds_cd.getText().toString();
                sds_nm=ds_nm.getText().toString();
                sst_cd=st_cd.getText().toString();
                //sst_nm=st_nm.getText().toString();
                boolean res=d2.distins(sds_cd,sds_nm,sst_cd,sst_nm);
                if(res==false)
                {
                    Toast.makeText(v.getContext(),"not inserted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(v.getContext(),"inserted",Toast.LENGTH_SHORT).show();
                    Intent m1=new Intent(v.getContext(),Register.class);
                    startActivity(m1);
                }
            }
        });
    }
}