package br.gov.ma.detran.examespraticosmobile.sqlite.handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_FaltaContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_LocalDeProvaContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_Prova_FaltasContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_Provas_CandidatosContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_UsuariosContract;

public class AGC_ExamesPraticosDbHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AGC_ExamesPraticosDb.db";
    public static final int DATABASE_VERSION = 6;

    public AGC_ExamesPraticosDbHandler(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AGC_UsuariosContract.SQL_CREATE_AGC_USUARIOS);
        db.execSQL(AGC_LocalDeProvaContract.SQL_CREATE_AGC_LOCAL_DE_PROVA);
        db.execSQL(AGC_Provas_CandidatosContract.SQL_CREATE_AGC_PROVAS_CONDIDATOS);
        db.execSQL(AGC_FaltaContract.SQL_CREATE_AGC_FALTAS);
        db.execSQL(AGC_Prova_FaltasContract.SQL_CREATE_AGC_PROVA_FALTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AGC_UsuariosContract.SQL_DELETE_AGC_USUARIOS);
        db.execSQL(AGC_LocalDeProvaContract.SQL_DELETE_AGC_LOCAL_DE_PROVA);
        db.execSQL(AGC_Provas_CandidatosContract.SQL_DELETE_AGC_PROVAS_CONDIDATOS);
        db.execSQL(AGC_FaltaContract.SQL_DELETE_AGC_FALTAS);
        db.execSQL(AGC_Prova_FaltasContract.SQL_DELETE_AGC_PROVA_FALTAS);
        onCreate(db);
    }
}
