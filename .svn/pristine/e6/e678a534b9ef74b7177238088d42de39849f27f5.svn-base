package br.gov.ma.detran.examespraticosmobile.sqlite.contract;

import android.provider.BaseColumns;

public class AGC_FaltaContract {

    public AGC_FaltaContract() { }

    public static final String SQL_CREATE_AGC_FALTAS =
            "CREATE TABLE " + AGC_FaltaEntry.TABLE_NAME + " (" +
                    AGC_FaltaEntry.COLUMN_TIPO_FALTA + " TEXT," +
                    AGC_FaltaEntry.COLUMN_ITEM_LETRA + " TEXT," +
                    AGC_FaltaEntry.COLUMN_DESCRIACO + " TEXT," +
                    AGC_FaltaEntry.COLUMN_PONTOS + " NUMERIC," +
                    AGC_FaltaEntry.COLUMN_TIPO_EXAME + " TEXT" +
                    ")";

    public static final String SQL_DELETE_AGC_FALTAS =
            "DROP TABLE IF EXISTS " + AGC_FaltaEntry.TABLE_NAME ;

    public static class AGC_FaltaEntry implements BaseColumns {

        public static final String TABLE_NAME = "AGC_Faltas";
        public static final String COLUMN_TIPO_FALTA = "tipoFalta";
        public static final String COLUMN_ITEM_LETRA = "itemLetra";
        public static final String COLUMN_DESCRIACO = "descricao";
        public static final String COLUMN_PONTOS = "pontos";
        public static final String COLUMN_TIPO_EXAME = "tipoExame";

    }

}
