package com.example.cdc.smartpan_task.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cdc.smartpan_task.R;
import com.example.cdc.smartpan_task.activities.Data.RegisterCallback;
import com.example.cdc.smartpan_task.activities.Data.User;
import com.example.cdc.smartpan_task.activities.Interface.PostFunctionsInterface;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.cdc.smartpan_task.activities.Interface.PostFunctionsInterface.API_URL;

public class RegisterActivity extends AppCompatActivity {

    Toolbar registerToolbar;
    EditText name, userName, password, mail, mobile;
    RadioGroup radioGroup;
    RadioButton male, female;
    Button register;
    ProgressBar progressBar;

    int genderValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();

        setSupportActionBar(registerToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Register");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                registerUser();
            }
        });
    }

    private void initialize(){
        registerToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        name = (EditText) findViewById(R.id.register_name_editText);
        userName = (EditText) findViewById(R.id.register_username_editText);
        password = (EditText) findViewById(R.id.register_password_editText);
        mail = (EditText) findViewById(R.id.register_email_editText);
        mobile = (EditText) findViewById(R.id.register_mobile_editText);
        radioGroup = (RadioGroup) findViewById(R.id.register_radioGroup);
        male = (RadioButton) findViewById(R.id.male_radioButton);
        female = (RadioButton) findViewById(R.id.female_radioButton);
        register = (Button) findViewById(R.id.register_submit);
        progressBar = (ProgressBar) findViewById(R.id.register_progressBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerUser(){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
        PostFunctionsInterface postFunctionsInterface = restAdapter.create(PostFunctionsInterface.class);

        final User user = new User();
        user.setName(name.getText().toString());
        user.setUserName(userName.getText().toString());
        user.setPassword(password.getText().toString());
        user.setMail(mail.getText().toString());
        user.setMobile(mobile.getText().toString());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkID) {

                switch(checkID){
                    case R.id.male_radioButton:
                        genderValue = 0;
                        user.setGender(genderValue);
                        break;
                    case R.id.female_radioButton:
                        genderValue = 1;
                        user.setGender(genderValue);
                        break;
                }
            }
        });

        postFunctionsInterface.registerUser(user, new Callback<RegisterCallback>() {
            @Override
            public void success(RegisterCallback registerCallback, Response response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
