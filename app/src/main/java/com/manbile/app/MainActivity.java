package com.manbile.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int TEMPO_SPLASH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {

            Intent intent =
                    new Intent(MainActivity.this, LoginActivity.class);

            startActivity(intent);

            finish();

        }, TEMPO_SPLASH);
    }
}