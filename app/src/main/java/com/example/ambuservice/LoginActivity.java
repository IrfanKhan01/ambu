package com.example.ambuservice;

//import android.support.design.widgpatActivity;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ambuservice.helper.Constants;
import com.example.ambuservice.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    FirebaseAuth auth;
    DatabaseReference dbRef;

    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.et_password) TextInputEditText etPassword;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.tv_create_acc)
    TextView tvCreateAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("Login");
        }

        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

    }


    @OnClick(R.id.btn_login)
    public void login() {
        boolean flag = true;

        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Phone Number Required!");
            flag = false;

        } else {
            etEmail.setError(null);
        }

        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password Required!");
            flag = false;

        } else {
            etPassword.setError(null);
        }

        if (!flag) {
            return;
        }

        auth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            getUserData(firebaseUser.getUid());

                        } else {
//                    Toast.makeText(LoginActivity.this, task.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getUserData(String userId) {
        dbRef.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    Paper.book().write(Constants.CURR_USER_KEY, user);
                    startMainActivity(user.roleId);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }


    public void startMainActivity(int userRole) {
        if (userRole == 0) {
            Intent intent = new Intent(this, DriverActivity.class);
            startActivity(intent);
            finish();


        } else {
            Intent intent = new Intent(this, ClientActivity.class);
            startActivity(intent);
            finish();

        }

    }


    @OnClick(R.id.tv_create_acc)
    public void setOpenSignUp() {
        Intent intent = new Intent(LoginActivity.this, UserRoleActivity.class);
        startActivity(intent);
    }


}
