package com.example.cdc.smartpan_task.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cdc.smartpan_task.R;
import com.example.cdc.smartpan_task.activities.Pref.Preference;

public class HomeActivity extends AppCompatActivity {

    Preference pref;
    String savedUserName;
    int savedUserID;
    TextView userNameTextView, userIDTextView;
    Button logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref = new Preference(this);
        pref.checkLogin();
        
        savedUserID = pref.getUserID();
        savedUserName = pref.getUserName();

        initialize();

        userNameTextView.setText(savedUserName);
        userIDTextView.setText(String.valueOf(savedUserID));

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.logoutUser();
            }
        });
    }

    private void initialize(){
        userIDTextView = (TextView) findViewById(R.id.userID_textView);
        userNameTextView = (TextView) findViewById(R.id.userName_textView);
        logoutButton = (Button) findViewById(R.id.logout_Button);
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
