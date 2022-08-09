package br.gov.ma.detran.examespraticosmobile.sqlite.contract;

import android.provider.BaseColumns;

public final class AGC_UsuariosContract {
    private AGC_UsuariosContract() {}

    public static final String SQL_CREATE_AGC_USUARIOS =
            "CREATE TABLE " + AGC_UsuariosContract.AGC_UsuariosEntry.TABLE_NAME + " (" +
                    AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_LOGIN + " TEXT," +
                    AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_CPFUSUARIO + " TEXT," +
                    AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_NOME + " TEXT," +
                    AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_SENHA + " TEXT," +
                    AGC_UsuariosContract.AGC_UsuariosEntry.COLUMN_TIPOUSUARIO + " TEXT)";

    public static final String SQL_DELETE_AGC_USUARIOS =
            "DROP TABLE IF EXISTS " + AGC_UsuariosContract.AGC_UsuariosEntry.TABLE_NAME;

    public static class AGC_UsuariosEntry implements BaseColumns {

        public static final String TABLE_NAME = "AGC_Usuarios";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LOGIN = "login";
        public static final String COLUMN_CPFUSUARIO = "cpfUsuario";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_SENHA = "senha";
        public static final String COLUMN_TIPOUSUARIO = "tipoUsuario";

    }
}
