package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    Button bt1,bt2,bt3;
    protected void onCreate(Bundle savedinstance)
    {
        super.onCreate(savedinstance);
        setContentView(R.layout.register);
        bt1=findViewById(R.id.state);
        bt2=findViewById(R.id.district);
        bt3=findViewById(R.id.mandal);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m1=new Intent(v.getContext(),State.class);
                startActivity(m1);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m2=new Intent(v.getContext(),District.class);
                startActivity(m2);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m3=new Intent(v.getContext(),Mandal.class);
                startActivity(m3);
            }
        });
    }
}
