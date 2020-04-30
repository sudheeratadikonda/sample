package com.example.sample.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsActivity extends AppCompatActivity {


    List<VoterData> voterDataListTotal;
    List<String> voterDataListMale;
    List<String> voterDataListFeMale;
    List<String> voterDataListMalePolled;
    List<String> voterDataListFeMalePolled;
    List<String> voterDataListPolled;

    String TAG = "FIREBASE_DATA";
    DatabaseReference myRefTotalVoters;
    @BindView(R.id.txtTotalVotes)
    TextView txtTotalVotes;
    @BindView(R.id.txtTotalVotesPolled)
    TextView txtTotalVotesPolled;
    @BindView(R.id.txtMaleVotes)
    TextView txtMaleVotes;
    @BindView(R.id.txtFemaleVotes)
    TextView txtFemaleVotes;
    @BindView(R.id.txtMaleVotesPolled)
    TextView txtMaleVotesPolled;
    @BindView(R.id.txtFeMaleVotesPolled)
    TextView txtFeMaleVotesPolled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statstics);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Statistics");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        myRefTotalVoters = FirebaseDatabase.getInstance().getReference("Voter_Details");

        voterDataListTotal = new ArrayList<>();
        voterDataListMale = new ArrayList<>();
        voterDataListFeMale = new ArrayList<>();
        voterDataListMalePolled = new ArrayList<>();
        voterDataListFeMalePolled = new ArrayList<>();
        voterDataListPolled = new ArrayList<>();

        //Total Voters
        myRefTotalVoters.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    voterDataListTotal.clear();
                    voterDataListMale.clear();
                    voterDataListFeMale.clear();
                    voterDataListPolled.clear();
                    voterDataListMalePolled.clear();
                    voterDataListFeMalePolled.clear();

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        VoterData voterData = dataSnapshot1.getValue(VoterData.class);
                        voterDataListTotal.add(voterData);

                    }

                    for (VoterData voterData : voterDataListTotal) {
                        String gender = String.valueOf(voterData.getVoterGender());
                        String status = String.valueOf(voterData.getVoterStatus());
                        String voterName = String.valueOf(voterData.getVoterName());

                        if (gender.equalsIgnoreCase("Male")) {
                            voterDataListMale.add(gender);
                        } else if (gender.equalsIgnoreCase("Female")) {
                            voterDataListFeMale.add(gender);
                        }

                        if (status.equalsIgnoreCase("Yes")) {
                            voterDataListPolled.add(status);
                        }

                        if (status.equalsIgnoreCase("Yes") && gender.equalsIgnoreCase("Male")) {
                            voterDataListMalePolled.add(voterName);

                        }
                        if (status.equalsIgnoreCase("Yes") && gender.equalsIgnoreCase("Female")) {
                            voterDataListFeMalePolled.add(voterName);

                        }


                    }

                    txtTotalVotes.setText("Total Voters Count: " + voterDataListTotal.size());
                    txtMaleVotes.setText("Total Male Voters Count : " + voterDataListFeMale.size());
                    txtFemaleVotes.setText("Total Female Voters Count : " + voterDataListFeMale.size());
                    txtTotalVotesPolled.setText("Total Voters Polled : " + voterDataListPolled.size());
                    txtMaleVotesPolled.setText("Total Male Voters Polled: " + voterDataListMalePolled.size());
                    txtFeMaleVotesPolled.setText("Total Female Voters Polled: " + voterDataListFeMalePolled.size());


                } else {
                    txtTotalVotes.setText("Total Voters Count: 0");
                    txtMaleVotes.setText("Total Male Voters Count : 0");
                    txtFemaleVotes.setText("Total Female Voters Count : 0");
                    txtTotalVotesPolled.setText("Total Voters Polled : 0");
                    txtMaleVotesPolled.setText("Total Male Voters Polled: 0");
                    txtFeMaleVotesPolled.setText("Total Female Voters Polled: 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatisticsActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
}
