package br.gov.ma.detran.examespraticosmobile.sqlite.operations;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Falta;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ListViewFaltas;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_FaltaContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_Prova_FaltasContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_Provas_CandidatosContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.handler.AGC_ExamesPraticosDbHandler;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_Prova_FaltasOperations {

    private static final String LOGTAG = "EMP_MNGMNT_SYS";

    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    public AGC_Prova_FaltasOperations(Context context){
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

    public void inserir(AGC_Prova_Falta agcProvaFalta) throws NegocioException {
        ContentValues contentValues = retornarContentValues_AGC_Prova_Falta(agcProvaFalta);
        long insert;
        if(agcProvaFalta.getId() == null){
            insert = database.insert(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME, null, contentValues);
        } else{
            insert = database.update(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME,
                    contentValues, AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO + " = ? ",
                    new String[]{String.valueOf(agcProvaFalta.getId())});
        }
        if (insert == -1){
            throw new NegocioException("Erro ao inserir registro");
        }
    }

    public void limparTabela() throws NegocioException {
        final int delete = database.delete(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME, null, null);
        if (delete ==-1)
            throw new NegocioException("Erro ao deletar registros");

    }

    public List<ListViewFaltas> retornarQuestoesParaCandidatoETipoDeExame(String cpfCandidato, String tipoExame, String tipoFalta){
        String sql = "SELECT (" + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_ITEM_LETRA + " || ') ' || " + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_DESCRIACO + ") descricao, "
                    + "(select " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_QUANTIDADE_FALTAS + " from " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME +
                    " where " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO + " = ? and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME + " = ? "
                    + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_FALTA +  " = ? "
                    + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_ITEM_LETRA + " = " + AGC_FaltaContract.AGC_FaltaEntry.TABLE_NAME + "." + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_ITEM_LETRA + ") quantidade "
                    + "FROM " + AGC_FaltaContract.AGC_FaltaEntry.TABLE_NAME + " where " + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_FALTA + " = ? "
                    + "and " + AGC_FaltaContract.AGC_FaltaEntry.COLUMN_TIPO_EXAME + " = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{cpfCandidato, tipoExame, tipoFalta, tipoFalta, tipoExame});

        List<ListViewFaltas> listViewFaltas = manipularCursorListViewFaltas(cursor);

        return listViewFaltas;
    }

    public List<AGC_Prova_Falta> retornarFaltasParaCandidatoETipoDeExame(String cpfCandidato, String dataExame, String localExame, String tipoExame, String tipoFalta){
        String sql = "select * from " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME +
                " where " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATA_EXAME + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_LOCAL_EXAME + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_FALTA +  " = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{cpfCandidato, dataExame, localExame, tipoExame, tipoFalta});

        List<AGC_Prova_Falta> listViewFaltas = manipularCursorList(cursor);

        return listViewFaltas;
    }

    public List<AGC_Prova_Falta> retornarFaltasParaCandidatoETipoDeExame(String cpfCandidato, String dataExame, String localExame, String tipoExame){
        String sql = "select * from " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME +
                " where " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATA_EXAME + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_LOCAL_EXAME + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME + " = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{cpfCandidato, dataExame, localExame, tipoExame});

        List<AGC_Prova_Falta> listViewFaltas = manipularCursorList(cursor);

        return listViewFaltas;
    }

    public AGC_Prova_Falta retornarQuestaoDoCandidato(String cpfCandidato, String dataExame, String localExame, String tipoExame, String tipoFalta, String itemLetra){

        String sql = "select * from " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME
                + " where " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATA_EXAME + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_LOCAL_EXAME + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_FALTA + " = ? "
                + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_ITEM_LETRA + " = ? "
                ;

        Cursor cursor = database.rawQuery(sql, new String[]{cpfCandidato, dataExame, localExame, tipoExame, tipoFalta, itemLetra});

        AGC_Prova_Falta agcProvaFalta = manipularCursor(cursor);

        return agcProvaFalta;
    }

    public void atualizarFalta(AGC_Prova_Falta agcProvaFalta) throws NegocioException {

        ContentValues contentValues = retornarContentValues_AGC_Prova_Falta(agcProvaFalta);

        final long update = database.update(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME,
                contentValues,
                AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATA_EXAME + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_LOCAL_EXAME + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_FALTA + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_ITEM_LETRA + " = ? ",
                new String[]{agcProvaFalta.getCpfCandidato(), agcProvaFalta.getDataExame(), agcProvaFalta.getLocalExame(),
                        agcProvaFalta.getTipoExame(),agcProvaFalta.getTipoFalta(), agcProvaFalta.getItemLetra()}
        );

        if (update ==-1)
            throw new NegocioException("Erro ao atualizar registro");
    }

    public void removerProvaFalta(AGC_Prova_Falta agcProvaFalta) throws NegocioException {
        final int delete = database.delete(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.TABLE_NAME,AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATA_EXAME + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_LOCAL_EXAME + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_FALTA + " = ? "
                        + "and " + AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_ITEM_LETRA + " = ? ",
                new String[]{agcProvaFalta.getCpfCandidato(), agcProvaFalta.getDataExame(), agcProvaFalta.getLocalExame(),
                        agcProvaFalta.getTipoExame(),agcProvaFalta.getTipoFalta(), agcProvaFalta.getItemLetra()}
        );

        if (delete ==-1)
            throw new NegocioException("Erro ao deletar registros");

    }

    private AGC_Prova_Falta manipularCursor(Cursor cursor){

        AGC_Prova_Falta agcProvaFalta = null;

        if (cursor.moveToNext()) {
            agcProvaFalta = setCursor(cursor);
        }

        return agcProvaFalta;

    }

    @SuppressLint("Range")
    private AGC_Prova_Falta setCursor(Cursor cursor){
        AGC_Prova_Falta agcProvaFalta = new AGC_Prova_Falta();
        agcProvaFalta.setId(cursor.getLong(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_ID)));
        agcProvaFalta.setCpfCandidato(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO)));
        agcProvaFalta.setDataExame(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATA_EXAME)));
        agcProvaFalta.setLocalExame(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_LOCAL_EXAME)));
        agcProvaFalta.setTipoExame(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME)));
        agcProvaFalta.setTipoFalta(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_FALTA)));
        agcProvaFalta.setItemLetra(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_ITEM_LETRA)));
        agcProvaFalta.setQuantidadeDeFaltas(cursor.getInt(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_QUANTIDADE_FALTAS)));
        agcProvaFalta.setCpfInclusao(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_INCLUSAO)));
        agcProvaFalta.setDataHoraInclusao(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATAHORA_INCLUSAO)));
        agcProvaFalta.setObservacoes(cursor.getString(cursor.getColumnIndex(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_OBSERVACOES)));

        return agcProvaFalta;
    }

    private List<AGC_Prova_Falta> manipularCursorList(Cursor cursor){

        List<AGC_Prova_Falta> lista = null;

        if(cursor.getCount() > 0) {
            lista = new ArrayList<AGC_Prova_Falta>();
            while (cursor.moveToNext()) {
                AGC_Prova_Falta agcProvaFalta = new AGC_Prova_Falta();
                agcProvaFalta = setCursor(cursor);
                lista.add(agcProvaFalta);
            }
        }

        return lista;
    }

    private List<ListViewFaltas> manipularCursorListViewFaltas(Cursor cursor){
        List<ListViewFaltas> lista = null;

        if(cursor.getCount() > 0) {
            lista = new ArrayList<ListViewFaltas>();
            while (cursor.moveToNext()) {
                ListViewFaltas l = new ListViewFaltas();
                l = setCursorListViewFaltas(cursor);
                lista.add(l);
            }
        }
        return lista;
    }

    private ListViewFaltas setCursorListViewFaltas(Cursor cursor){
        ListViewFaltas listViewFaltas = null;

        listViewFaltas = new ListViewFaltas();
        listViewFaltas.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));

        String quantidade = cursor.getString(cursor.getColumnIndex("quantidade"));
        if (quantidade == null || quantidade.isEmpty()){
            quantidade = "0";
        }

        listViewFaltas.setQuantidade(quantidade);

        return listViewFaltas;
    }

    private ContentValues retornarContentValues_AGC_Prova_Falta(AGC_Prova_Falta agcProvaFalta){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_ID, agcProvaFalta.getId());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO, agcProvaFalta.getCpfCandidato());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO, agcProvaFalta.getCpfCandidato());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATA_EXAME, agcProvaFalta.getDataExame());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_LOCAL_EXAME, agcProvaFalta.getLocalExame());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME, agcProvaFalta.getTipoExame());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_TIPO_FALTA, agcProvaFalta.getTipoFalta());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_ITEM_LETRA, agcProvaFalta.getItemLetra());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_QUANTIDADE_FALTAS, agcProvaFalta.getQuantidadeDeFaltas());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_CPF_INCLUSAO, agcProvaFalta.getCpfInclusao());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_DATAHORA_INCLUSAO, agcProvaFalta.getDataHoraInclusao());
        contentValues.put(AGC_Prova_FaltasContract.AGC_Prova_FaltasEntry.COLUMN_OBSERVACOES, agcProvaFalta.getObservacoes());
        return contentValues;
    }

}
