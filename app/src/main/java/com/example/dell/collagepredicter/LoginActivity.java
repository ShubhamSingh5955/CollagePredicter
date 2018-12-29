package com.example.dell.collagepredicter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailText,passwordText;
    private Button loginButton,registerButton;
    private ProgressDialog mprogressDialog;
    private Toolbar mToolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText=(TextInputLayout)findViewById(R.id.login_email);
        passwordText=(TextInputLayout)findViewById(R.id.login_password);
        loginButton=(Button)findViewById(R.id.login_button);

        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("literary");

        mprogressDialog=new ProgressDialog(this);

        mToolbar=(Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = emailText.getEditText().getText().toString();
                String password = passwordText.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mprogressDialog=new ProgressDialog(LoginActivity.this);
                    mprogressDialog.setTitle("Login User");
                    mprogressDialog.setMessage("please wait while we check your credentials..");
                    mprogressDialog.show();
                    mprogressDialog.setCanceledOnTouchOutside(false);


                    login(email, password);

                }
            }});
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mprogressDialog.dismiss();

                            Intent intent=new Intent(LoginActivity.this,AddActivity.class);
                            startActivity(intent);


                        } else {

                            mprogressDialog.hide();

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
