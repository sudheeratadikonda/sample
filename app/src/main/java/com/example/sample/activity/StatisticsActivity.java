package com.example.sample.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;
import com.example.sample.modals.VoterData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsActivity extends AppCompatActivity {

    @BindView(R.id.txtFemaleVotes)
    TextView txtFemaleVotes;
    @BindView(R.id.txtMaleVotes)
    TextView txtMaleVotes;
    @BindView(R.id.txtFemaleVotesPolled)
    TextView txtFemaleVotesPolled;
    @BindView(R.id.txtMaleVotesPolled)
    TextView txtMaleVotesPolled;
    @BindView(R.id.txtTotalVotesPolled)
    TextView getTxtTotalVotesPolled;
    DatabaseReference myRefTotalVoters;
    Long totalVoters,totalVotersPolled;
    TextView txtTotalVotes;
    long maleVoters,femaleVoters,maleVotersPolled,femaleVotersPolled;

    List<VoterData> voterDataList;
    List<String> voterDataList1;

    String TAG="FIREBASE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statstics);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Statistics");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        txtTotalVotes = (TextView) findViewById(R.id.txtTotalVotes);

        myRefTotalVoters = FirebaseDatabase.getInstance().getReference("Voter_Details");

        voterDataList=new ArrayList<>();
        voterDataList1=new ArrayList<>();

         //Total Voters
        myRefTotalVoters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    voterDataList.clear();
                    voterDataList1.clear();
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        VoterData voterData=dataSnapshot1.getValue(VoterData.class);
                        String data= String.valueOf(dataSnapshot1.child("voterGender").getValue(String.class).equalsIgnoreCase("Female"));

                        voterDataList.add(voterData);
                        voterDataList1.add(data);


                    }

                    //totalVoters = dataSnapshot.getChildrenCount();
                    Log.d(TAG, "onDataChange: "+voterDataList.size());

                    txtTotalVotes.setText("Total Voters : " +voterDataList.size());

                    Log.d(TAG, "onDataChange: "+voterDataList1.size());

                } else {
                    txtTotalVotes.setText("Total Voters : 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatisticsActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



       /*//Total Voters Polled
        Query query = myRefTotalVoters.orderByChild("voterStatus").equalTo("Yes");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    totalVotersPolled = dataSnapshot.getChildrenCount();
                    getTxtTotalVotesPolled.setText("Voting Polled : "+ totalVotersPolled);
                } else {
                    getTxtTotalVotesPolled.setText("Voting Polled : 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatisticsActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Total male Voters
        Query query1 = myRefTotalVoters.orderByChild("voterGender").equalTo("Male");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    maleVoters = dataSnapshot.getChildrenCount();
                    txtMaleVotes.setText("Male Voters : "+ maleVoters);
                } else {
                    txtMaleVotes.setText("Male Voters : 0");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatisticsActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Total Female Voters

        Query query2 = myRefTotalVoters.orderByChild("voterGender").equalTo("Female");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    femaleVoters = dataSnapshot.getChildrenCount();
                    if(femaleVoters!=0)
                    txtFemaleVotes.setText("Female Voters : "+ femaleVoters);

                } else {
                    txtFemaleVotes.setText("Female Voters : 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatisticsActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Query query3 = myRefTotalVoters.orderByChild("voterGender").equalTo("Male").orderByChild("voterStatus").equalTo("Yes");
        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    maleVotersPolled = dataSnapshot.getChildrenCount();

                   *//*
                   Query query4 = .orderByChild("voterStatus").equalTo("Yes");
                    query4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                maleVotersPolled = dataSnapshot.getChildrenCount();
                                txtMaleVotesPolled.setText("Male Voters Polled : "+ maleVotersPolled);
                            } else {
                                txtMaleVotesPolled.setText("Male Voters Polled : 0");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(StatisticsActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });*//*
                } else {
                    txtMaleVotesPolled.setText("Male Voters Polled : 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query5 = myRefTotalVoters.orderByChild("voterGender").equalTo("Female");
        query5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    Query query6 = query5.orderByChild("voterStatus").equalTo("Yes");
                    query6.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                femaleVotersPolled = dataSnapshot.getChildrenCount();
                                txtFemaleVotesPolled.setText("Female Voters Polled : "+ femaleVotersPolled);
                            } else {
                                txtFemaleVotesPolled.setText("Female Voters Polled : 0");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(StatisticsActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    txtFemaleVotesPolled.setText("Female Voters Polled : 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
