package com.shahbazly_dev.orthoepy_use.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.shahbazly_dev.orthoepy_use.Database.DBHelper;
import com.shahbazly_dev.orthoepy_use.R;
import com.stephentuso.welcome.WelcomeHelper;

public class MainActivity extends AppCompatActivity {

    WelcomeHelper welcomeScreen;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeScreen = new WelcomeHelper(this, WelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

        Button button = (Button)findViewById(R.id.btn_learning);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudyActivity.class);
                startActivity(intent);
            }
        });
        dbHelper = new DBHelper(this,1);
        dbHelper.getWritableDatabase();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }
}
