package ru.valerevich.dmitriy.practicegoogle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.valerevich.dmitriy.practicegoogle.recyclerview.MainActivityLessonThree;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.example_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Class<? extends Activity> activityClass;

        switch (v.getId()) {
            case R.id.example_1: activityClass = MainActivityLessonThree.class; break;

            default: activityClass = null;
        }
        if (activityClass != null) {
            startActivity(new Intent(this, activityClass));
        }
    }
}
