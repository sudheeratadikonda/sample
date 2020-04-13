package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
*/

public class Alogin extends AppCompatActivity implements View.OnClickListener {
    Button bt;
    EditText id,pass;
    String ld,lpass;

    protected void onCreate(Bundle savedInstance)
    {

        super.onCreate(savedInstance);
        setContentView(R.layout.adlogin);
        bt=findViewById(R.id.login);
        id=findViewById(R.id.regnol);
        pass=findViewById(R.id.passwordl);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ld=id.getText().toString();
                lpass=pass.getText().toString();
                if(ld.equals("admin@gmail.com")&&lpass.equals("Admin@123")) {

                    Intent i;
                    i = new Intent(v.getContext(), Activityselect.class);
                    startActivity(i);
                    id.setText("");
                    pass.setText("");
                }
                else
                {
                    Toast.makeText(Alogin.this, "Unauthorised Admin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
    }
}

