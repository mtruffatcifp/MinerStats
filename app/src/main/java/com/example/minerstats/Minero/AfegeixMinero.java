package com.example.minerstats.Minero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minerstats.BBDD.InterficieBBDD;
import com.example.minerstats.Crypto.Crypto;
import com.example.minerstats.R;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AfegeixMinero extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private InterficieBBDD bd;
    private EditText editNom, editQtyGPU, editIp;
    private AutoCompleteTextView editCrypto;
    private Spinner spinnerCrypto;
    private Button btnGuardar, btnCancelar;
    private ArrayList<Crypto> llista_crypto;
    private List<String> array_crypto_string = new ArrayList<String>();
    private Minero minero = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegeix_minero);

        bd = new InterficieBBDD(this);
        bd.obre();

        editNom = findViewById(R.id.nomMinero);
        editQtyGPU = findViewById(R.id.qtyGPU);
        editCrypto = findViewById(R.id.editCrypto);
        editIp = findViewById(R.id.ipMinero);
        spinnerCrypto = findViewById(R.id.spinnerCrypto);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);


        spinnerCrypto.setOnItemSelectedListener(this);
        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);


        rellenarSpinnerCrypto();
        ArrayAdapter<String> adapterCrypto = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, array_crypto_string);
        editCrypto.setAdapter(adapterCrypto);

        spinnerCrypto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editCrypto.setText(spinnerCrypto.getSelectedItem().toString());
                editCrypto.dismissDropDown();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editCrypto.setText(spinnerCrypto.getSelectedItem().toString());
                editCrypto.dismissDropDown();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnGuardar) {
            Minero minero = generaObjecteMinero();

            if (minero == null) {
                Toast.makeText(this, "Rellena tots els camps!", Toast.LENGTH_SHORT).show();
            } else if (bd.createMinero(minero).getId() != -1) {
                Toast.makeText(this, "Afegit correctament", Toast.LENGTH_SHORT).show();
                bd.tanca();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Error a l’afegir BBDD", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnCancelar) {
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void rellenarSpinnerCrypto() {
        llista_crypto = bd.getAllCrypto();

        for (Crypto c : llista_crypto) {
            array_crypto_string.add(c.getNom() + " ( " + c.getId() + " )");
        }
        // Spinner Drop down elements
        List<String> string_crypto = new ArrayList<String>();
        string_crypto.add("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (int i = 0; i < llista_crypto.size(); i++) {
                string_crypto.add(llista_crypto.get(i).getNom() + " (" + llista_crypto.get(i).getId() + ")");
            }
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.text_spinner, string_crypto);
        // attaching data adapter to spinner
        spinnerCrypto.setAdapter(dataAdapter);
        spinnerCrypto.setSelection(0);
    }

    public Minero generaObjecteMinero() {
        if (!editNom.getText().toString().isEmpty() && !editQtyGPU.getText().toString().isEmpty() && !editIp.getText().toString().isEmpty()) {
            minero = new Minero();
            minero.setNom(editNom.getText().toString());
            minero.setQtyGPU(Integer.parseInt(editQtyGPU.getText().toString()));
            minero.setId_crypto(editCrypto.getText().toString().split("\\(")[1].replace(")", ""));
            minero.setIp_minero(editIp.getText().toString());
        }
        return minero;
    }
}