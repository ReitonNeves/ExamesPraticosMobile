package br.gov.ma.detran.examespraticosmobile.sqlite.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Falta;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_FaltaContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.handler.AGC_ExamesPraticosDbHandler;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_FaltaOperations {

    public static final String LOGTAG = "EMP_MNGMNT_SYS";

    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    public AGC_FaltaOperations(Context context){
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

    public AGC_Falta retornarFalta(String itemLetra, String tipoFalta, String tipoExame){

        String sql = "select * from " + AGC_FaltaContract.AGC_FaltaEntry.TABLE_NAME
                + " where " + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_ITEM_LETRA + " = ? "
                + " and " + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_FALTA + " = ? "
                + " and " + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_EXAME + " = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{itemLetra, tipoFalta, tipoExame});

        AGC_Falta agcFalta = manipularCursor(cursor);

        cursor.close();

        return agcFalta;

    }

    public List<AGC_Falta> listarTodosPorTipo(String tipoExame){

        String sql = "select * from " + AGC_FaltaContract.AGC_FaltaEntry.TABLE_NAME
                + " where " + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_EXAME + " = ? "
                + " order by " + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_ITEM_LETRA;

        Cursor cursor = database.rawQuery(sql, new String[] {tipoExame});

        List<AGC_Falta> lista = manipularCursorList(cursor);

        cursor.close();

        return lista;

    }

    public List<AGC_Falta> listarTodos(){

        String sql = "select * from " + AGC_FaltaContract.AGC_FaltaEntry.TABLE_NAME;

        Cursor cursor = database.rawQuery(sql, null);

        List<AGC_Falta> lista = manipularCursorList(cursor);

        cursor.close();

        return lista;

    }

    public void inserir(AGC_Falta agcFalta) throws NegocioException {

        ContentValues contentValues = manipularContentValue(agcFalta);
        final long insert = database.insert(AGC_FaltaContract.AGC_FaltaEntry.TABLE_NAME, null, contentValues);

        if (insert ==-1)
            throw new NegocioException("Erro ao inserir registro");
    }

    public void limparDados() throws NegocioException {
        final int delete = database.delete(AGC_FaltaContract.AGC_FaltaEntry.TABLE_NAME, null, null);
        if (delete ==-1)
            throw new NegocioException("Erro ao deletar registros");

    }

    private AGC_Falta manipularCursor(Cursor cursor){

        AGC_Falta agcFalta = null;

        if (cursor.moveToNext()) {
            agcFalta = new AGC_Falta();
            agcFalta = setCursor(cursor);
        }

        return agcFalta;

    }

    private List<AGC_Falta> manipularCursorList(Cursor cursor){

        List<AGC_Falta> lista = null;

        if(cursor.getCount() > 0) {
            lista = new ArrayList<AGC_Falta>();
            while (cursor.moveToNext()) {
                AGC_Falta agcFalta = new AGC_Falta();
                agcFalta = setCursor(cursor);
                lista.add(agcFalta);
            }
        }

        return lista;

    }

    private AGC_Falta setCursor(Cursor cursor){

        AGC_Falta agcFalta = null;

        agcFalta = new AGC_Falta();
        agcFalta.setDescricao(cursor.getString(cursor.getColumnIndex(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_DESCRIACO)));
        agcFalta.setItemLetra(cursor.getString(cursor.getColumnIndex(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_ITEM_LETRA)));
        agcFalta.setPontos(cursor.getInt(cursor.getColumnIndex(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_PONTOS)));
        agcFalta.setTipoExame(cursor.getString(cursor.getColumnIndex(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_EXAME)));
        agcFalta.setTipoFalta(cursor.getString(cursor.getColumnIndex(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_FALTA)));

        return agcFalta;

    }

    private ContentValues manipularContentValue(AGC_Falta agcFalta){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_DESCRIACO, agcFalta.getDescricao());
        contentValues.put(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_ITEM_LETRA, agcFalta.getItemLetra());
        contentValues.put(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_FALTA, agcFalta.getTipoFalta());
        contentValues.put(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_PONTOS, agcFalta.getPontos());
        contentValues.put(AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_EXAME, agcFalta.getTipoExame());

        return contentValues;
    }

}
