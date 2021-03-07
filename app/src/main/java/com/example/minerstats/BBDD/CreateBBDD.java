package com.example.minerstats.BBDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CreateBBDD extends SQLiteOpenHelper {
    //Declaració general BBDD
    public static final String TAG = "InterficieBBDD";
    public static final String BD_NOM = "MinerStatsDB";
    public static final String BD_TAULA_MINERO = "minero";
    public static final String BD_TAULA_CRYPTO = "crypto";
    public static final String BD_TAULA_GPU = "gpu";
    public static final String BD_TAULA_HISTORIAL = "historial";
    public static final int VERSIO = 1;


    //Declaració de constants classe Minero
    public static final String CLAU_ID_MINERO = "_id";
    public static final String CLAU_NOM_MINERO = "nom";
    public static final String CLAU_QTYGPU = "qtyGPU";
    public static final String CLAU_REL_CRYPTO = "id_crypto";
    public static final String CLAU_IP_MINERO = "ip_minero";


    public static final String BD_CREATE_MINERO = "CREATE TABLE " + BD_TAULA_MINERO + "( " + CLAU_ID_MINERO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CLAU_NOM_MINERO + " TEXT NOT NULL, " + CLAU_QTYGPU + " INTEGER, " + CLAU_REL_CRYPTO + " TEXT, " + CLAU_IP_MINERO + " TEXT NOT NULL);";


    //Declaracio taula Crypto
    public static final String CLAU_ID_CRYPTO = "_id";
    public static final String CLAU_NOM = "nom";

    public static final String BD_CREATE_CRYPTO = "CREATE TABLE " + BD_TAULA_CRYPTO + "( " + CLAU_ID_CRYPTO + " TEXT PRIMARY KEY, " + CLAU_NOM + " TEXT NOT NULL);";


    //Declaracio taula Gpu
    public static final String CLAU_ID_GPU = "_id";
    public static final String CLAU_NOM_GPU = "nom";
    public static final String CLAU_REL_MINERO= "id_minero";

    public static final String BD_CREATE_GPU = "CREATE TABLE " + BD_TAULA_GPU + "(" + CLAU_ID_GPU + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CLAU_NOM_GPU + " TEXT NOT NULL, " + CLAU_REL_MINERO + " INTEGER);";


    //Declaracio taula Historial
    public static final String CLAU_ID_HISTORIAL = "_id";
    public static final String CLAU_DATAHORA = "data_hora";
    public static final String CLAU_TEMPERATURA = "temperatura";
    public static final String CLAU_REL_GPU = "id_gpu";

    public static final String BD_CREATE_HISTORIAL = "CREATE TABLE " + BD_TAULA_HISTORIAL + "( " + CLAU_ID_HISTORIAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CLAU_DATAHORA + " TEXT NOT NULL, " + CLAU_TEMPERATURA + " INTEGER, " + CLAU_REL_GPU + " INTEGER);";


    public CreateBBDD(@Nullable Context context) {
        super(context, BD_NOM, null, VERSIO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(BD_CREATE_MINERO);
        db.execSQL(BD_CREATE_CRYPTO);
        db.execSQL(BD_CREATE_GPU);
        db.execSQL(BD_CREATE_HISTORIAL);
        db.execSQL(" INSERT INTO "+BD_TAULA_CRYPTO+" (_id, nom) values ('ETH', 'Ethereum')");
        db.execSQL(" INSERT INTO "+BD_TAULA_CRYPTO+" (_id, nom) values ('RVN', 'Raven Coin')");
        db.execSQL(" INSERT INTO "+BD_TAULA_CRYPTO+" (_id, nom) values ('ETC', 'Ethereum Classic')");
        db.execSQL(" INSERT INTO "+BD_TAULA_CRYPTO+" (_id, nom) values ('BTC', 'Bitcoin')");
        db.execSQL(" INSERT INTO "+BD_TAULA_CRYPTO+" (_id, nom) values ('BCH', 'Bitcoin Cash')");
        db.execSQL(" INSERT INTO "+BD_TAULA_CRYPTO+" (_id, nom) values ('XMR', 'Monero')");
        db.execSQL(" INSERT INTO "+BD_TAULA_CRYPTO+" (_id, nom) values ('EGEM', 'EtherGem')");
        db.execSQL(" INSERT INTO "+BD_TAULA_CRYPTO+" (_id, nom) values ('LTC', 'LiteCoin')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Detecta si hi ha una canvi a DATABASE_VERSION i recrea la base de dades
        Log.w(CreateBBDD.class.getName(), "Modificant desde la versió " + oldVersion + " a "+ newVersion );
        db.execSQL("DROP TABLE IF EXISTS " + BD_TAULA_MINERO);
        db.execSQL("DROP TABLE IF EXISTS " + BD_TAULA_CRYPTO);
        db.execSQL("DROP TABLE IF EXISTS " + BD_TAULA_GPU);
        db.execSQL("DROP TABLE IF EXISTS " + BD_TAULA_HISTORIAL);
        onCreate(db);
    }
}