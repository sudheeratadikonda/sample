package com.example.sample;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

public class State extends AppCompatActivity {
    Button bt1;
    EditText st,st_cd;
    String sst,sst_cd;
    DatabaseHelper db;
    protected void onCreate(Bundle savedinstance) {
        super.onCreate(savedinstance);
        setContentView(R.layout.state);
        Stetho.initializeWithDefaults(this);
        db=new DatabaseHelper(this);
        st=findViewById(R.id.sstate);
        st_cd=findViewById(R.id.statec);
        bt1=findViewById(R.id.sregister);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sst=st.getText().toString();
                sst_cd=st_cd.getText().toString();
                boolean res=db.stateins(sst,sst_cd);
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
