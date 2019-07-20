package com.example.ambuservice;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ambuservice.helper.Constants;
import com.example.ambuservice.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class MyProfileActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email) TextView tvEmail;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.tv_cnic) TextView tvCNIC;

    User currUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("My Profile");
            bar.setDisplayHomeAsUpEnabled(true);
        }

        currUser = Paper.book().read(Constants.CURR_USER_KEY);

        tvName.setText(currUser.name);
        tvEmail.setText(currUser.email);
        tvPhone.setText(currUser.phone);
        tvCNIC.setText(currUser.cnic);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
