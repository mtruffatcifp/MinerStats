package com.example.minerstats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Help extends AppCompatActivity implements View.OnClickListener {
    EditText comentari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        comentari = findViewById(R.id.comentari);
        FloatingActionButton confirma = findViewById(R.id.sendComplain);
        confirma.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendComplain:
                String comentari_enviar = comentari.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@minerstats.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Ajuda");
                intent.putExtra(Intent.EXTRA_TEXT, comentari_enviar);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Escull la seva app de mail preferida"));

                break;
            case R.id.torna:
                finish();
            default:
                break;
        }
    }
}