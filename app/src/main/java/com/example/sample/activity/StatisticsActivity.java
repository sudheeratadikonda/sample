package com.example.sample.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;
import com.example.sample.modals.VoterData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticsActivity extends AppCompatActivity {


    List<VoterData> voterDataListTotal;
    List<String> voterDataListMale;
    List<String> voterDataListFeMale;
    List<String> voterDataListMalePolled;
    List<String> voterDataListFeMalePolled;
    List<String> voterDataListPolled;

    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    double result, result1, result2, result3, result4, countTotal, count, count1, count2, count3, count4;

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
    ProgressDialog progressDialog;
    @BindView(R.id.btnSearchVote)
    Button btnSearchVote;
    @BindView(R.id.btnSearchVoteList)
    Button btnSearchVoteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statstics);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Statistics");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Statistics...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        myRefTotalVoters = FirebaseDatabase.getInstance().getReference("Voter_Details");

        voterDataListTotal = new ArrayList<>();
        voterDataListMale = new ArrayList<>();
        voterDataListFeMale = new ArrayList<>();
        voterDataListMalePolled = new ArrayList<>();
        voterDataListFeMalePolled = new ArrayList<>();
        voterDataListPolled = new ArrayList<>();


        //getEntries();
        getData();


    }


    public void getData() {

        //Total Voters
        myRefTotalVoters.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();
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


                        countTotal = voterDataListTotal.size();

                        count = voterDataListPolled.size();
                        result = Math.round((count * 100) / countTotal);

                        count1 = voterDataListMale.size();
                        result1 = Math.round((count1 * 100) / countTotal);

                        count2 = voterDataListFeMale.size();
                        result2 = Math.round((count2 * 100) / countTotal);

                        count3 = voterDataListMalePolled.size();
                        result3 = Math.round((count3 * 100) / countTotal);

                        count4 = voterDataListFeMalePolled.size();
                        result4 = Math.round((count4 * 100) / countTotal);


                        txtTotalVotes.setText("Total Voters Count: " + voterDataListTotal.size());
                        txtTotalVotesPolled.setText("Total Voters Polled : " + voterDataListPolled.size() + " (" + result + " % )");
                        txtMaleVotes.setText("Total Male Voters Count : " + voterDataListMale.size() + " (" + result1 + " % )");
                        txtFemaleVotes.setText("Total Female Voters Count : " + voterDataListFeMale.size() + " (" + result2 + " % )");
                        txtMaleVotesPolled.setText("Total Male Voters Polled: " + voterDataListMalePolled.size() + " (" + result3 + " % )");
                        txtFeMaleVotesPolled.setText("Total Female Voters Polled: " + voterDataListFeMalePolled.size() + " (" + result4 + " % )");




                    }



                   /* Log.d(TAG, "getEntries: "+(int)countTotal);
                    pieEntries = new ArrayList<>();
                    pieEntries.add(new PieEntry((int)countTotal, 0));
                    pieEntries.add(new PieEntry((int)count, 1));
                    pieEntries.add(new PieEntry((int)count1, 2));
                    pieEntries.add(new PieEntry(3f, 3));
                    pieEntries.add(new PieEntry(7f, 4));
                    pieEntries.add(new PieEntry((int)countTotal, 5));*/


                    progressDialog.dismiss();


                } else {
                    txtTotalVotes.setText("Total Voters Count: 0");
                    txtMaleVotes.setText("Total Male Voters Count : 0");
                    txtFemaleVotes.setText("Total Female Voters Count : 0");
                    txtTotalVotesPolled.setText("Total Voters Polled : 0");
                    txtMaleVotesPolled.setText("Total Male Voters Polled: 0");
                    txtFeMaleVotesPolled.setText("Total Female Voters Polled: 0");
                    progressDialog.dismiss();
                }

                getEntries();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatisticsActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEntries() {
        pieChart = findViewById(R.id.pieChart);
        Log.d(TAG, "getEntries: " + countTotal + "  _____" + count + "  _____" + count1 + "  _____" + count2 + "  _____" + count3 + "  _____" + count4);
        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry((float) 10.0, 0));
        pieEntries.add(new PieEntry(4f, 1));
        pieEntries.add(new PieEntry(6f, 2));
        pieEntries.add(new PieEntry(8f, 3));
        pieEntries.add(new PieEntry(7f, 4));
        pieEntries.add(new PieEntry(3f, 5));

        pieDataSet = new PieDataSet(pieEntries, "Voters Data");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnSearchVote, R.id.btnSearchVoteList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSearchVote:
                Intent intent = new Intent(StatisticsActivity.this, VoterSearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.btnSearchVoteList:
                Intent intent1 = new Intent(StatisticsActivity.this, VoterListActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;
        }
    }
}
