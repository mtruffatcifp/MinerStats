package com.example.minerstats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.minerstats.BBDD.InterficieBBDD;
import com.example.minerstats.Minero.AfegeixMinero;
import com.example.minerstats.Minero.Minero;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int ADD_CODE = 1;
    private InterficieBBDD bd;
    private ArrayList<Minero> llistaMineros;
    private RecyclerView recyclerViewGenere, recyclerViewBSO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar que mola molt
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivityForResult(new Intent(view.getContext(), AfegeixMinero.class),ADD_CODE);
            }
        });
    }
}