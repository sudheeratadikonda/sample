package com.example.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.btnState)
    Button btnState;
    @BindView(R.id.btnDistrict)
    Button btnDistrict;
    @BindView(R.id.btnMandal)
    Button btnMandal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Registration Page");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


    }


    @OnClick({R.id.btnState, R.id.btnDistrict, R.id.btnMandal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnState:
                Intent intent = new Intent(RegistrationActivity.this, AddStateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btnDistrict:
                Intent intent1 = new Intent(RegistrationActivity.this, AddDistrictActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.btnMandal:
                Intent intent2 = new Intent(RegistrationActivity.this, AddMandalActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
