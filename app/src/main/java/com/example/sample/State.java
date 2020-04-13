package com.example.sample;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.modals.StateData;
import com.facebook.stetho.Stetho;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class State extends AppCompatActivity {
    Button bt1;
    EditText edtstate,edtstatecode;
    String state,statecode;
    DatabaseReference myref;
    protected void onCreate(Bundle savedinstance) {
        super.onCreate(savedinstance);
        setContentView(R.layout.state);
        Stetho.initializeWithDefaults(this);

        myref= FirebaseDatabase.getInstance().getReference().child("State_Data");

        edtstate=findViewById(R.id.sstate);
        edtstatecode=findViewById(R.id.statecode);
        bt1=findViewById(R.id.sregister);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=edtstate.getText().toString();
                statecode=edtstatecode.getText().toString();

                if(state.isEmpty()){
                    edtstate.setError("Please enter State Name");
                } else if(statecode.isEmpty()) {
                    edtstatecode.setError("Please enter State Code");
                } else {
                     myref.push().setValue(new StateData(state,statecode));

                    Toast.makeText(State.this, "Record Successfully Inserted !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(State.this,Activityselect.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }



                //boolean res=db.stateins(sst,sst_cd);
                /*if(res==false)
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
