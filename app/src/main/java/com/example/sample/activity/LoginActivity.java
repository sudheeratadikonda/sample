package com.example.sample.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sample.R;
import com.example.sample.utilities.ConstantValues;
import com.example.sample.utilities.MyAppPrefsManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    MyAppPrefsManager myAppPrefsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth=FirebaseAuth.getInstance();
        myAppPrefsManager=new MyAppPrefsManager(LoginActivity.this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
                String pwd = Objects.requireNonNull(etPassword.getText()).toString().trim();

                if(email.isEmpty()) {
                    etEmail.setError("Please enter Email ID");
                } else if(pwd.isEmpty()) {
                    etPassword.setError("Please enter Password");
                } else {
                    progressDialog.show();
                    loginUser(email,pwd);
                }
            }
        });
    }

    public void loginUser(String email,String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    myAppPrefsManager.setAdminLoggedIn(true);
                    ConstantValues.IS_USER_LOGGED_IN_ADMIN = myAppPrefsManager.isAdminLoggedIn();
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
