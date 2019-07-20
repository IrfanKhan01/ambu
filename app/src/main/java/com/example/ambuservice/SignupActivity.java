package com.example.ambuservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.ambuservice.helper.Constants;
import com.example.ambuservice.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    TextInputEditText etName;
    @BindView(R.id.et_email) TextInputEditText etEmail;
    @BindView(R.id.et_password) TextInputEditText etPassword;
    @BindView(R.id.et_phone) TextInputEditText etPhone;
    @BindView(R.id.et_cnic) TextInputEditText etCNIC;
    @BindView(R.id.btn_register)
    Button btnRegister;

    FirebaseAuth auth;
    DatabaseReference dbRef;
    int roleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("Registration");
        }

        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        roleId = getIntent().getIntExtra(Constants.USER_ROLE_KEY, 0);


    }



    @OnClick(R.id.btn_register)
    public void register() {
        boolean flag = true;

        if (etName.getText().toString().isEmpty()) {
            etName.setError("Required!");
            flag = false;
        } else {
            etName.setError(null);
        }

        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Required");
            flag = false;

        } else {
            etEmail.setError(null);
        }

        if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError("Required!");
            flag = false;

        } else {
            etPhone.setError(null);
        }

        if (etCNIC.getText().toString().isEmpty()) {
            etCNIC.setError("Required!");
            flag = false;

        } else {
            etCNIC.setError(null);
        }

        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Required!");
            flag = false;

        } else {
            etPassword.setError(null);
        }

        if (!flag) {
            return;
        }

        auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            final User user = new User();
                            user.id = firebaseUser.getUid();
                            user.name = etName.getText().toString().trim();
                            user.email = etEmail.getText().toString().trim();
                            user.phone = etPhone.getText().toString().trim();
                            user.cnic = etCNIC.getText().toString().trim();


                            user.roleId = roleId;

                            dbRef.child("users").child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                        } else {
                            // if sign up fails, display a message
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
