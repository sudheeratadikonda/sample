package com.example.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.R;
import com.example.sample.utilities.ConstantValues;
import com.example.sample.utilities.MyAppPrefsManager;
import com.google.firebase.auth.FirebaseAuth;

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

    MyAppPrefsManager myAppPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home Page");
        myAppPrefsManager=new MyAppPrefsManager(HomePageActivity.this);

    }

    @OnClick({R.id.btnRegister, R.id.btnUploadDetails, R.id.btnVoter, R.id.btnStatistcs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                Intent intent = new Intent(HomePageActivity.this, RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btnUploadDetails:
                Intent intent1 = new Intent(HomePageActivity.this, UploadVoterDetailsActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

                break;
            case R.id.btnVoter:
                Intent intent2 = new Intent(HomePageActivity.this, VoteActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);

                break;
            case R.id.btnStatistcs:
                Intent intent3 = new Intent(HomePageActivity.this, StatisticsActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            myAppPrefsManager.setAdminLoggedIn(false);
            ConstantValues.IS_USER_LOGGED_IN_ADMIN = myAppPrefsManager.isAdminLoggedIn();
            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}
