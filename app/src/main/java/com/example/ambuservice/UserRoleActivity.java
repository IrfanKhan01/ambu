package com.example.ambuservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.ambuservice.helper.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRoleActivity extends AppCompatActivity {

    @BindView(R.id.btn_client) Button btnClient;
    @BindView(R.id.btn_driver) Button btnDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_role);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_client)
    public void setBtnClient() {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra(Constants.USER_ROLE_KEY, 1);
        startActivity(intent);
    }

    @OnClick(R.id.btn_driver)
    public void setBtnDriver() {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra(Constants.USER_ROLE_KEY, 0);
        startActivity(intent);
    }
}
