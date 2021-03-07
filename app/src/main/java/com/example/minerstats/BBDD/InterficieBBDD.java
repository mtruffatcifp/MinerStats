package com.example.minerstats.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.minerstats.Crypto.Crypto;
import com.example.minerstats.Gpu.Gpu;
import com.example.minerstats.Minero.Minero;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;

public class InterficieBBDD {

    private final Context context;
    private CreateBBDD ajuda;
    private SQLiteDatabase bd;

    private String[] allColumnsMinero = {CreateBBDD.CLAU_ID_MINERO, CreateBBDD.CLAU_NOM_MINERO, CreateBBDD.CLAU_QTYGPU, CreateBBDD.CLAU_REL_CRYPTO, CreateBBDD.CLAU_IP_MINERO};

    private String[] allColumnsCrypto = {CreateBBDD.CLAU_ID_CRYPTO, CreateBBDD.CLAU_NOM};

    private String[] allColumnsGPU = {CreateBBDD.CLAU_ID_GPU, CreateBBDD.CLAU_NOM_GPU, CreateBBDD.CLAU_ID_MINERO};

    private String[] allColumnsHistorial = {CreateBBDD.CLAU_ID_HISTORIAL, CreateBBDD.CLAU_DATAHORA, CreateBBDD.CLAU_TEMPERATURA, CreateBBDD.CLAU_REL_GPU};


    public InterficieBBDD(Context con) {
        this.context = con;
        ajuda = new CreateBBDD(context);
    }

    //Obre la Base de dades
    public void obre() throws SQLException {
        bd = ajuda.getWritableDatabase();
    }

    //Tanca la Base de dades
    public void tanca() {
        ajuda.close();
    }

    public Minero createMinero(Minero minero) {
        // insert d'una nova peli
        ContentValues values = new ContentValues();
        values.put(CreateBBDD.CLAU_NOM_MINERO, minero.getNom());
        values.put(CreateBBDD.CLAU_QTYGPU, minero.getQtyGPU());
        values.put(CreateBBDD.CLAU_REL_CRYPTO, minero.getId_crypto());
        values.put(CreateBBDD.CLAU_IP_MINERO, minero.getIp_minero());
        long insertId = bd.insert(CreateBBDD.BD_TAULA_MINERO, null, values);
        minero.setId(insertId);
        return minero;
    }

    public ArrayList<Minero> getAllMinero() {
        ArrayList<Minero> mineros = new ArrayList<Minero>();
        Cursor cursor = bd.query(CreateBBDD.BD_TAULA_MINERO, allColumnsMinero, null, null, null, null, CreateBBDD.CLAU_ID_MINERO + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Minero minero = cursorToMinero(cursor);
            mineros.add(minero);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return mineros;
    }

    //Cercador de mineros (escrit)
    public ArrayList<Minero> consultaMinero(String regex) {
        ArrayList<Minero> mineros = new ArrayList<Minero>();
        Cursor cursor = bd.query(true, CreateBBDD.BD_TAULA_MINERO, allColumnsMinero,CreateBBDD.CLAU_NOM + " LIKE ?", new String[] { regex+"%" }, null, null, CreateBBDD.CLAU_NOM + " ASC", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Minero minero = cursorToMinero(cursor);
            mineros.add(minero);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return mineros;
    }


    private Minero cursorToMinero(Cursor cursor) {
        Minero minero = new Minero();

        minero.setId(cursor.getLong(0));
        minero.setNom(cursor.getString(1));
        minero.setQtyGPU(cursor.getInt(2));
        minero.setId_crypto(cursor.getString(3));
        minero.setIp_minero(cursor.getString(4));
        Log.w("ObjetoMinero", minero.getNom());
        return minero;
    }

    //Retorna un Minero
    public Minero obtenirMinero(long IDFila) throws SQLException {
        Cursor mCursor = bd.query(true, CreateBBDD.BD_TAULA_MINERO, allColumnsMinero,CreateBBDD.CLAU_ID_MINERO + " = " + IDFila, null, null, null, null, null);
        if(mCursor != null) {
            mCursor.moveToFirst();
        }
        return cursorToMinero(mCursor);
    }

    //Modifica un minero a partir del id
    public boolean actualitzaMinero(long IDFila, Minero minero) {
        ContentValues values = new ContentValues();
        Log.w("aaaa", minero.getNom());
        values.put(CreateBBDD.CLAU_NOM_MINERO, minero.getNom());
        values.put(CreateBBDD.CLAU_QTYGPU, minero.getQtyGPU());
        values.put(CreateBBDD.CLAU_REL_CRYPTO, minero.getId_crypto());
        values.put(CreateBBDD.CLAU_IP_MINERO, minero.getIp_minero());
        return bd.update(CreateBBDD.BD_TAULA_MINERO, values, CreateBBDD.CLAU_ID_MINERO + " = " + IDFila, null) > 0;
    }

    //Esborra un contacte
    public boolean esborraMinero(long IDFila) {
        return bd.delete(CreateBBDD.BD_TAULA_MINERO, CreateBBDD.CLAU_ID_MINERO + " = " + IDFila, null) > 0;
    }



    //lLISTA GÈNERES
    public ArrayList<Crypto> getAllCrypto() {
        ArrayList<Crypto> llista = new ArrayList<Crypto>();
        Cursor cursor = bd.query(CreateBBDD.BD_TAULA_CRYPTO, allColumnsCrypto, null, null, null, null, CreateBBDD.CLAU_NOM + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Crypto crypto = cursorToCrypto(cursor);
            llista.add(crypto);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return llista;
    }

    private Crypto cursorToCrypto(Cursor cursor) {
        Crypto crypto = new Crypto();
        crypto.setId(cursor.getString(0));
        crypto.setNom(cursor.getString(1));
        return crypto;
    }

    //Retorna una crypto a partir de un id
    public String obtenirCrypto(String IDFila) throws SQLException {
        Cursor mCursor = bd.query(true, CreateBBDD.BD_TAULA_CRYPTO, allColumnsCrypto,CreateBBDD.CLAU_ID_CRYPTO + " LIKE " + IDFila, null, null, null, null, null);
        if(mCursor != null) {
            mCursor.moveToFirst();
        }
        return cursorToCrypto(mCursor).getNom();
    }

    public Gpu createGpu(Gpu gpu) {
        ContentValues values = new ContentValues();
        values.put(CreateBBDD.CLAU_ID_GPU, gpu.getId());
        values.put(CreateBBDD.CLAU_NOM_GPU, gpu.getNom());
        values.put(CreateBBDD.CLAU_REL_MINERO, gpu.getId_minero());
        long insertId = bd.insert(CreateBBDD.BD_TAULA_GPU, null, values);
        gpu.setId(insertId);
        return gpu;
    }

    //Llista totes les BSO
    public ArrayList<Gpu> llistaGpu() {
        ArrayList<Gpu> gpus = new ArrayList<Gpu>();
        Cursor cursor = bd.query(CreateBBDD.BD_TAULA_GPU, allColumnsGPU, null, null, null, null, CreateBBDD.CLAU_ID_GPU + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Gpu gpu = cursorToGpu(cursor);
            gpus.add(gpu);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return gpus;
    }


    private Gpu cursorToGpu(Cursor cursor) {
        Gpu gpu = new Gpu();
        gpu.setId(cursor.getLong(0));
        gpu.setNom(cursor.getString(1));
        gpu.setId_minero(cursor.getLong(2));
        return gpu;
    }

    //Retorna una Gpu
    public Gpu obtenirGpu(long IDFila) throws SQLException {
        Cursor mCursor = bd.query(true, CreateBBDD.BD_TAULA_GPU, allColumnsGPU,CreateBBDD.CLAU_ID_GPU + " = " + IDFila, null, null, null, null, null);
        if(mCursor != null) {
            mCursor.moveToFirst();
        }
        return cursorToGpu(mCursor);
    }

    //Falta historial!!!
}
