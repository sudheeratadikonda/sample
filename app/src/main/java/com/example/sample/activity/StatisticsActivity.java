package com.example.sample.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsActivity extends AppCompatActivity {

    @BindView(R.id.txtTotalVotes)
    TextView txtTotalVotes;
    @BindView(R.id.txtFemaleVotes)
    TextView txtFemaleVotes;
    @BindView(R.id.txtMaleVotes)
    TextView txtMaleVotes;
    @BindView(R.id.txtFemaleVotesPolled)
    TextView txtFemaleVotesPolled;
    @BindView(R.id.txtMaleVotesPolled)
    TextView txtMaleVotesPolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statstics);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Statistics");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        txtTotalVotes.setText("% Voting Polled : "+"5000");
        txtFemaleVotes.setText("% Female Voters : "+"2000");
        txtMaleVotes.setText("% Male Voters : "+"3000");
        txtFemaleVotesPolled.setText("% Female Voters Polled : "+"2000");
        txtMaleVotesPolled.setText("% Male Voters Polled : "+"3000");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
