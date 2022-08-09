package br.gov.ma.detran.examespraticosmobile.sqlite.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_LocalDeProva;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_LocalDeProvaContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.handler.AGC_ExamesPraticosDbHandler;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_LocalDeProvaOperations {

    private static final String LOGTAG = "EMP_MNGMNT_SYS";

    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    private static final String[] allColumns = {
            AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_ID,
            AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_DESCRICAO};

    public AGC_LocalDeProvaOperations(Context context){
        dbhandler = new AGC_ExamesPraticosDbHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public void inserir(AGC_LocalDeProva agcLocalDeProva) throws NegocioException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_ID, agcLocalDeProva.getId());
        contentValues.put(AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_DESCRICAO, agcLocalDeProva.getDescricao());
        final long insert = database.insert(AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.TABLE_NAME, null, contentValues);

        if (insert ==-1)
            throw new NegocioException("Erro ao inserir registro");
    }

    public AGC_LocalDeProva retornarPorID(long id){

        String sql = "select * from " + AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.TABLE_NAME
                + " where " + AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});

        AGC_LocalDeProva agcLocalDeProva = null;

        if (cursor.moveToFirst()) {
            agcLocalDeProva = new AGC_LocalDeProva(cursor.getLong(0), cursor.getString(1));
        }

        cursor.close();

        return agcLocalDeProva;

    }

    public AGC_LocalDeProva retornarPorDescricao(String descricao){

        String sql = "select * from " + AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.TABLE_NAME
                + " where " + AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_DESCRICAO + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(descricao)});

        AGC_LocalDeProva agcLocalDeProva = null;

        if (cursor.moveToFirst()) {
            agcLocalDeProva = new AGC_LocalDeProva(cursor.getLong(0), cursor.getString(1));
        }

        cursor.close();

        return agcLocalDeProva;

    }

    public List<AGC_LocalDeProva> retornarTodosOrdenadoPorId(){

        String sql = "select * from " + AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.TABLE_NAME
                + " order by " + AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_ID;

        Cursor cursor = database.rawQuery(sql, null);

        List<AGC_LocalDeProva> agcLocalDeProvaList;
        agcLocalDeProvaList = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                AGC_LocalDeProva agcLocalDeProva = new AGC_LocalDeProva();
                agcLocalDeProva.setId(cursor.getLong(cursor.getColumnIndex(AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_ID)));
                agcLocalDeProva.setDescricao(cursor.getString(cursor.getColumnIndex(AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_DESCRICAO)));
                agcLocalDeProvaList.add(agcLocalDeProva);
            }
        }

        cursor.close();

        return agcLocalDeProvaList;

    }

    public List<AGC_LocalDeProva> retornarTodosOrdenadoPorDescricao(){

        String sql = "select * from " + AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.TABLE_NAME
                + " order by " + AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_DESCRICAO;

        Cursor cursor = database.rawQuery(sql, null);

        List<AGC_LocalDeProva> agcLocalDeProvaList;
        agcLocalDeProvaList = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                AGC_LocalDeProva agcLocalDeProva = new AGC_LocalDeProva();
                agcLocalDeProva.setId(cursor.getLong(cursor.getColumnIndex(AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_ID)));
                agcLocalDeProva.setDescricao(cursor.getString(cursor.getColumnIndex(AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.COLUMN_DESCRICAO)));
                agcLocalDeProvaList.add(agcLocalDeProva);
            }
        }

        cursor.close();

        return agcLocalDeProvaList;

    }

    public void limparDados() throws NegocioException {
        final int delete = database.delete(AGC_LocalDeProvaContract.AGC_LocalDeProvaEntry.TABLE_NAME, null, null);
        if (delete ==-1)
            throw new NegocioException("Erro ao deletar registros");

    }

}
