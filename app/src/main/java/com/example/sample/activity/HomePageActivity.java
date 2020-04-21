package com.example.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomePageActivity extends AppCompatActivity {

    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnUploadDetails)
    Button btnUploadDetails;
    @BindView(R.id.btnVoter)
    Button btnVoter;
    @BindView(R.id.btnStatistcs)
    Button btnStatistcs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home Page");

    }

    @OnClick({R.id.btnRegister, R.id.btnUploadDetails, R.id.btnVoter, R.id.btnStatistcs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                startActivity(new Intent(HomePageActivity.this,RegistrationActivity.class));
                break;
            case R.id.btnUploadDetails:
                break;
            case R.id.btnVoter:
                break;
            case R.id.btnStatistcs:
                break;
        }
    }
}
