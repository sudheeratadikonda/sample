package com.example.sample.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMandalActivity extends AppCompatActivity {

    @BindView(R.id.spinStateName)
    Spinner spinStateName;
    @BindView(R.id.spinStateCode)
    Spinner spinStateCode;
    @BindView(R.id.spinDistName)
    Spinner spinDistName;
    @BindView(R.id.spinDistCode)
    Spinner spinDistCode;
    @BindView(R.id.etMandalName)
    TextInputEditText etMandalName;
    @BindView(R.id.etMandalCode)
    TextInputEditText etMandalCode;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mandal);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Mandal");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
    }
}
