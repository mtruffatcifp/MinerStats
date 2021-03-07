package com.example.minerstats.Minero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.minerstats.BBDD.InterficieBBDD;
import com.example.minerstats.Crypto.Crypto;
import com.example.minerstats.R;

import java.util.ArrayList;
import java.util.List;

public class MineroDetall extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private InterficieBBDD bd;
    private ArrayList<Crypto> llista_cryptos;

    private int RESULT_OK = 1;
    private List<String> array_crypto_string = new ArrayList<String>();
    private Minero miner;
    private long idMinero;
    private Button btnTorna, btnEditar, btnGuardar;

    private EditText editNom, editQtyGPU, editCrypto, editIp;
    private Spinner spinnerCrypto;
    private Crypto cripto = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minero_detall);
        Bundle extras = getIntent().getExtras();

        idMinero = extras.getLong("idMinero");
        //idMinero = 1;
        Log.w("idMiner", idMinero+"");
        bd = new InterficieBBDD(this.getApplicationContext());
        bd.obre();
        miner = bd.obtenirMinero(idMinero);

        editNom = findViewById(R.id.nom);
        editQtyGPU = findViewById(R.id.numGPU);
        editIp = findViewById(R.id.ip);

        //Botons i spinner
        btnTorna = (Button) findViewById(R.id.torna);
        btnEditar = (Button) findViewById(R.id.actualitza);
        btnGuardar = (Button) findViewById(R.id.guardar);
        spinnerCrypto = (Spinner) findViewById(R.id.spinnerCryptoEdit);

        spinnerCrypto.setOnItemSelectedListener(this);
        btnTorna.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);

        showInfo(miner);
    }

    @Override
    public void onClick(View v) {
        if (v == btnTorna) {
            bd.tanca();
            finish();
        }else if(v == btnEditar){
            setEditTextEnabled();
        }else if(v == btnGuardar){
            bd.actualitzaMinero(idMinero, getInfo());
            bd.tanca();
            finish();
        }
    }

    public Minero getInfo(){
        miner.setNom(editNom.getText().toString());
        miner.setQtyGPU(Integer.parseInt(editQtyGPU.getText().toString()));
        miner.setId_crypto(spinnerCrypto.getSelectedItem().toString().split("\\(")[1].replace(")", ""));
        miner.setIp_minero(editIp.getText().toString());
        return miner;
    }

    private void setEditTextEnabled() {
        editNom.setEnabled(true);
        editQtyGPU.setEnabled(true);
        //editCrypto.setEnabled(true);
        editIp.setEnabled(true);
    }

    public void showInfo(Minero m){
        editNom.setText(m.getNom());
        editQtyGPU.setText(m.getQtyGPU()+"");
        //editCrypto.setText(m.getId_crypto());
        editIp.setText(m.getIp_minero());
        ferSpinnerCrypto();
    }

    private void ferSpinnerCrypto() {
        llista_cryptos = bd.getAllCrypto();

        // Spinner Drop down elements
        List<String> string_crypto = new ArrayList<String>();
        string_crypto.add("");
        int cryptoSeleccionat = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (int i = 0; i < llista_cryptos.size(); i++) {
                if (miner.getId_crypto().equals(llista_cryptos.get(i).getId())) cryptoSeleccionat = i;
                string_crypto.add(llista_cryptos.get(i).getNom() + " (" + llista_cryptos.get(i).getId() + ")");
            }
        }
        Log.w("marco", cryptoSeleccionat+"");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.text_spinner, string_crypto);

        // attaching data adapter to spinner
        spinnerCrypto.setAdapter(dataAdapter);
        spinnerCrypto.setSelection(++cryptoSeleccionat);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0){
            if(parent.getId() == R.id.spinnerCrypto) {
                cripto = llista_cryptos.get(position-1);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}