package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activityselect extends AppCompatActivity implements View.OnClickListener {
    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activityselect);
        bt1 = findViewById(R.id.register);
        bt1.setOnClickListener(this);
        bt2 = findViewById(R.id.upload);
        bt2.setOnClickListener(this);
        bt3 = findViewById(R.id.update);
        bt3.setOnClickListener(this);
        bt4 = findViewById(R.id.delete);
        bt4.setOnClickListener(this);

    }
    @Override
    public void onClick(View v1) {
        if (v1 == bt1) {
            Intent i1;
            i1 = new Intent(v1.getContext(), Register.class);
            startActivity(i1);
        }
        if (v1 == bt2) {
            Intent i2;
            i2 = new Intent(v1.getContext(), Activityselect.class);
            startActivity(i2);
        }
        if (v1 == bt3) {
            Intent i3;
            i3 = new Intent(v1.getContext(), Activityselect.class);
            startActivity(i3);
        }
        if (v1 == bt4) {
            Intent i4;
            i4 = new Intent(v1.getContext(), Activityselect.class);
            startActivity(i4);
        }
    }
}

