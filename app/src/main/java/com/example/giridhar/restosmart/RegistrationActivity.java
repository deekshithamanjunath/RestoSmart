package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail,etPassword;
    Button btRegister;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        etEmail =(EditText)findViewById(R.id.editText5);
        etPassword=(EditText)findViewById(R.id.editText7);
        btRegister =(Button)findViewById(R.id.button2);

        firebaseAuth =FirebaseAuth.getInstance();
        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
     String email    = etEmail.getText().toString();
     String password = etPassword.getText().toString();
     if(email.isEmpty())
     {
         etEmail.requestFocus();
         etEmail.setError("Please Enter Valid Email");
     }
     else if(password.isEmpty() || password.length()<6)
     {
         etPassword.requestFocus();
         etPassword.setError("Please Enter Valid Password");
     }
     else
     {
         firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task)
             {
              if(task.isSuccessful())
              {
                  Toast.makeText(RegistrationActivity.this,"You have been registered successfully",Toast.LENGTH_SHORT).show();
                  Intent login = new Intent(RegistrationActivity.this,LoginActivity.class);
                  finish();
                  startActivity(login);
              }
              else
              {
                 Toast.makeText(RegistrationActivity.this,"Error registering",Toast.LENGTH_SHORT).show();
              }
             }
         });
     }
    }
}
