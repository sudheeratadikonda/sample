package com.example.sample.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sample.R;
import com.example.sample.modals.VoterData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoteActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.txtId)
    TextView txtId;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtMandal)
    TextView txtMandal;
    @BindView(R.id.txtDistrict)
    TextView txtDistrict;
    @BindView(R.id.txtState)
    TextView txtState;
    DatabaseReference myref;
    String voterId, imgUrl, name, mandal, district, state, status;
    ProgressDialog progressDialog;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.detailsLayout)
    LinearLayout detailsLayout;
    @BindView(R.id.btnVote)
    Button btnVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Voter");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        myref = FirebaseDatabase.getInstance().getReference("Voter_Details");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        etSearch.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voterId = etSearch.getText().toString().trim();
                if (voterId.isEmpty()) {
                    etSearch.setError("Please enter Voter ID");
                } else {
                    progressDialog.show();

                    Query query = myref.orderByChild("voterID").equalTo(voterId);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    imgUrl = Objects.requireNonNull(dataSnapshot1.getValue(VoterData.class)).getImageUrl();
                                    name = Objects.requireNonNull(dataSnapshot1.getValue(VoterData.class)).getVoterName();
                                    mandal = Objects.requireNonNull(dataSnapshot1.getValue(VoterData.class)).getVoterMandal();
                                    district = Objects.requireNonNull(dataSnapshot1.getValue(VoterData.class)).getVoterDistrict();
                                    state = Objects.requireNonNull(dataSnapshot1.getValue(VoterData.class)).getVoterState();
                                    status = Objects.requireNonNull(dataSnapshot1.getValue(VoterData.class)).getVoterStatus();
                                }
                                getUserDetails();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(VoteActivity.this, "No Voter Found !!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(VoteActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnVote)
    public void onViewClicked() {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                VoteActivity.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure want to vote this voterId  ?");
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),"No is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();

}

    void getUserDetails() {
        detailsLayout.setVisibility(View.VISIBLE);
        Glide.with(this).load(imgUrl).into(image);
        txtId.setText("Voter ID  :  " + voterId);
        txtName.setText("Name  :  " + name);
        txtMandal.setText("Mandal  :  " + mandal);
        txtDistrict.setText("District  :  " + district);
        txtState.setText("State  :  " + state);
        txtStatus.setText("Status  :  " + status);

        if (status.equalsIgnoreCase("Yes")){
            btnVote.setVisibility(View.GONE);
        }else {
            btnVote.setVisibility(View.VISIBLE);
        }
        progressDialog.dismiss();
    }
}
