package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
EditText etMailId,etPassword;
TextView tvEmail,tvPassword,tvRegister;
Button btLogin;
FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etMailId =(EditText)findViewById(R.id.editText4);
        etPassword =(EditText)findViewById(R.id.editText6);
        tvEmail =(TextView)findViewById(R.id.textView20);
        tvPassword=(TextView)findViewById(R.id.textView22);
        tvRegister =(TextView)findViewById(R.id.textView23);
        btLogin = (Button)findViewById(R.id.loginButton);
        firebaseauth =FirebaseAuth.getInstance();
        btLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.loginButton:
                              loginUser();
                              break;
            case R.id.textView23:
                               Intent navigate = new Intent(LoginActivity.this,RegistrationActivity.class);
                               startActivity(navigate);
                               break;


        }

    }

    private void loginUser()
    {
        String email    =  etMailId.getText().toString();
        String password = etPassword.getText().toString();
        if(email.isEmpty())
        {
            //
        }
        else if(password.isEmpty() || password.length() < 6)
        {
            //
        }
        else {
            if (email.equals("manager@gmail.com"))
            {

                firebaseauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                            etMailId.setText("");
                            etPassword.setText("");
//                            Intent i = new Intent(LoginActivity.this, TableViewActivity.class);
//                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
            else
            {
                firebaseauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                            etMailId.setText("");
                            etPassword.setText("");
                            Intent i = new Intent(LoginActivity.this, TableViewActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        }
    }
}
