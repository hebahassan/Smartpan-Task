package com.example.cdc.smartpan_task.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cdc.smartpan_task.R;
import com.example.cdc.smartpan_task.activities.Data.Login;
import com.example.cdc.smartpan_task.activities.Data.LoginCallback;
import com.example.cdc.smartpan_task.activities.Interface.PostFunctionsInterface;
import com.example.cdc.smartpan_task.activities.Pref.Preference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.cdc.smartpan_task.activities.Interface.PostFunctionsInterface.API_URL;

public class LoginActivity extends AppCompatActivity {

    Toolbar loginToolbar;
    EditText userName, password;
    Button login, register;
    ProgressBar progressBar;

    Preference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = new Preference(this);

        initialize();

        /**
        * Set Activity Toolbar
        * */
        setSupportActionBar(loginToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Login");
        }

        /**
        * Register Button Event
        * */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        /**
        * Login Button Event
        * */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                LoginUser();
            }
        });
    }

    /**
    * Initialize Layout Components
    * */
    private void initialize(){
        loginToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        userName = (EditText) findViewById(R.id.username_editText);
        password = (EditText) findViewById(R.id.password_editText);
        login = (Button) findViewById(R.id.login_Button);
        register = (Button) findViewById(R.id.register_Button);
        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
    }

    /**
    * Calling login function in Post interface
    * */
    private void LoginUser(){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
        PostFunctionsInterface postFunctionsInterface = restAdapter.create(PostFunctionsInterface.class);

        Login login = new Login();
        login.setUserName(userName.getText().toString());
        login.setPassword(password.getText().toString());

        postFunctionsInterface.loginUser(login, new Callback<LoginCallback>() {

            //Login Succeeded
            @Override
            public void success(LoginCallback loginCallback, Response response) {
                progressBar.setVisibility(View.GONE);

                int loginUserID = loginCallback.getUserID();
                String loginUserName = loginCallback.getUserName();
                pref.createLoginSession(loginUserID, loginUserName);

                startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
            }

            //Login Failed
            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
