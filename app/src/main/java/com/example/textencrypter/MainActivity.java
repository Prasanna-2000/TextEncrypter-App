package com.example.textencrypter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aesButton = findViewById(R.id.aes_button);
        Button md5Button = findViewById(R.id.md5_button);

        getSupportActionBar().hide();

        aesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aesIntent = new Intent(MainActivity.this, aes.class);
                startActivity(aesIntent);
            }
        });

        md5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent md5Intent = new Intent(MainActivity.this, md5.class);
                startActivity(md5Intent);
            }
        });
    }
}