package com.example.sample.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoteActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Voter");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getUserDetails();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
    }

    void getUserDetails(){
        txtId.setText("VoterId : Vote3245");
        txtName.setText("Name : Suresh");
        txtMandal.setText("Mandal : Kotturu");
        txtDistrict.setText("District : Vizag");
        txtState.setText("State : Ap");
    }
}
