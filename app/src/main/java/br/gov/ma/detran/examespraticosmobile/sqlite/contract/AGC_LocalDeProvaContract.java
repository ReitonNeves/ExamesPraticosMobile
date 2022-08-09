package br.gov.ma.detran.examespraticosmobile.sqlite.contract;

import android.provider.BaseColumns;

public class AGC_LocalDeProvaContract {

    private AGC_LocalDeProvaContract() {}

   public static final String SQL_CREATE_AGC_LOCAL_DE_PROVA =
            "CREATE TABLE " + AGC_LocalDeProvaEntry.TABLE_NAME + " (" +
                    AGC_LocalDeProvaEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    AGC_LocalDeProvaEntry.COLUMN_DESCRICAO + " TEXT)";

    public static final String SQL_DELETE_AGC_LOCAL_DE_PROVA =
            "DROP TABLE IF EXISTS " + AGC_LocalDeProvaEntry.TABLE_NAME;

    public static class AGC_LocalDeProvaEntry implements BaseColumns {

        public static final String TABLE_NAME = "AGC_LocalDeProva";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESCRICAO = "descricao";

    }
}
