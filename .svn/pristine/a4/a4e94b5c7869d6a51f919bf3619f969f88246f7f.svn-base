package br.gov.ma.detran.examespraticosmobile.sqlite.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.sqlite.contract.AGC_UsuariosContract;
import br.gov.ma.detran.examespraticosmobile.sqlite.handler.AGC_ExamesPraticosDbHandler;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class AGC_UsuariosOperations {

    public static final String LOGTAG = "EMP_MNGMNT_SYS";

    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    private static final String[] allColumns = {
            AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_ID,
            AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_LOGIN,
            AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_CPFUSUARIO,
            AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_NOME,
            AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_SENHA,
            AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_TIPOUSUARIO};

    public AGC_UsuariosOperations(Context context){
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

    public void inserir(AGC_Usuario agcUsuario) throws NegocioException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_ID, agcUsuario.getIdUsuario());
        contentValues.put(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_LOGIN, agcUsuario.getLogin());
        contentValues.put(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_CPFUSUARIO, agcUsuario.getCpfUsuario());
        contentValues.put(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_NOME, agcUsuario.getNome());
        contentValues.put(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_SENHA, agcUsuario.getSenha());
        contentValues.put(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_TIPOUSUARIO, agcUsuario.getTipoUsuario());
        final long insert = database.insert(AGC_UsuariosContract.AGC_UsuariosEntry.TABLE_NAME, null, contentValues);

        if (insert ==-1)
            throw new NegocioException("Erro ao inserir registro");
    }

    public AGC_Usuario retornarPorID(long id){

        String sql = "select * from " + AGC_UsuariosContract.AGC_UsuariosEntry.TABLE_NAME
                        + " where " + AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});

        AGC_Usuario agcUsuario = null;

        if (cursor.moveToFirst()) {
            agcUsuario = new AGC_Usuario(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }

        cursor.close();

        return agcUsuario;

    }

    public AGC_Usuario retornarPorLoginSenha(String login, String senha){

        String sql = "select * from " + AGC_UsuariosContract.AGC_UsuariosEntry.TABLE_NAME
                + " where " + AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_LOGIN
                + " = ? and " + AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_SENHA + " = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{login, senha});

        AGC_Usuario agcUsuario = null;

        if (cursor.moveToFirst()) {
            agcUsuario = new AGC_Usuario(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }

        cursor.close();

        return agcUsuario;

    }

    public List<AGC_Usuario> retornarTodos(){

        String sql = "select * from " + AGC_UsuariosContract.AGC_UsuariosEntry.TABLE_NAME
                + " order by " + AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_ID;

        Cursor cursor = database.rawQuery(sql, null);

        List<AGC_Usuario> agcUsuarios;
        agcUsuarios = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                AGC_Usuario agcUsuario = new AGC_Usuario();
                agcUsuario.setIdUsuario(cursor.getLong(cursor.getColumnIndex(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_ID)));
                agcUsuario.setCpfUsuario(cursor.getString(cursor.getColumnIndex(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_CPFUSUARIO)));
                agcUsuario.setNome(cursor.getString(cursor.getColumnIndex(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_NOME)));
                agcUsuario.setLogin(cursor.getString(cursor.getColumnIndex(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_LOGIN)));
                agcUsuario.setSenha(cursor.getString(cursor.getColumnIndex(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_SENHA)));
                agcUsuario.setTipoUsuario(cursor.getString(cursor.getColumnIndex(AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_TIPOUSUARIO)));
                agcUsuarios.add(agcUsuario);
            }
        }

        cursor.close();

        return agcUsuarios;

    }

    public void limparDados() throws NegocioException {
        final int delete = database.delete(AGC_UsuariosContract.AGC_UsuariosEntry.TABLE_NAME, null, null);
        if (delete ==-1)
            throw new NegocioException("Erro ao deletar registros");

    }

}
