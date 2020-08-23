package com.tanvi.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class infoActivity extends AppCompatActivity {

    private Button goToSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        goToSplash = (Button) findViewById(R.id.backbtn);

        goToSplash.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent Intent = new Intent(infoActivity.this, SplashActivity.class);
                                            startActivity(Intent);
                                        }
                                    }

        );

    }
}