package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Mandal extends AppCompatActivity {
    Button bt1;
    DatabaseHelper d3;
    EditText stnm,stc,dstnm,dstc,mnnm,mnc;
    String sstnm,sstc,sdstnm,sdstc,smnnm,smnc;
    protected void onCreate(Bundle savedinstance) {
        super.onCreate(savedinstance);
        setContentView(R.layout.mandal);
        bt1=findViewById(R.id.mregister);
        d3=new DatabaseHelper(this);
        stnm=findViewById(R.id.mstate);
        stc=findViewById(R.id.mstatec);
        dstnm=findViewById(R.id.mdistrict);
        dstc=findViewById(R.id.mdistrictc);
        mnnm=findViewById(R.id.mandal);
        mnc=findViewById(R.id.mandalc);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sstnm = stnm.getText().toString();
                sstc = stc.getText().toString();
                sdstnm = dstnm.getText().toString();
                sdstc = dstc.getText().toString();
                smnnm = mnnm.getText().toString();
                smnc = mnc.getText().toString();
                boolean res = d3.mndlins(smnc, smnnm, sdstc, sdstnm, sstc, sstnm);
                if (res == false) {
                    Toast.makeText(v.getContext(), "not inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(),"inserted",Toast.LENGTH_SHORT).show();
                    Intent m1 = new Intent(v.getContext(), Register.class);
                    startActivity(m1);
                }
            }
        });


    }
}

