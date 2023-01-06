package br.gov.ma.detran.examespraticosmobile.sqlite.contract;

import android.provider.BaseColumns;

public class AGC_Prova_FaltasContract {

    public AGC_Prova_FaltasContract() { }

    public static final String SQL_CREATE_AGC_PROVA_FALTAS =
            "CREATE TABLE " + AGC_Prova_FaltasEntry.TABLE_NAME + " (" +
                    AGC_Prova_FaltasEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    AGC_Prova_FaltasEntry.COLUMN_CPF_CANDIDATO + " TEXT," +
                    AGC_Prova_FaltasEntry.COLUMN_DATA_EXAME + " TEXT," +
                    AGC_Prova_FaltasEntry.COLUMN_LOCAL_EXAME + " TEXT," +
                    AGC_Prova_FaltasEntry.COLUMN_TIPO_EXAME + " TEXT," +
                    AGC_Prova_FaltasEntry.COLUMN_TIPO_FALTA + " TEXT," +
                    AGC_Prova_FaltasEntry.COLUMN_ITEM_LETRA + " TEXT," +
                    AGC_Prova_FaltasEntry.COLUMN_QUANTIDADE_FALTAS + " NUMERIC," +
                    AGC_Prova_FaltasEntry.COLUMN_CPF_INCLUSAO + " TEXT," +
                    AGC_Prova_FaltasEntry.COLUMN_DATAHORA_INCLUSAO + " TEXT," +
                    AGC_Prova_FaltasEntry.COLUMN_OBSERVACOES + " TEXT" +
                    ")";

    public static final String SQL_DELETE_AGC_PROVA_FALTAS = "DROP TABLE IF EXISTS " + AGC_Prova_FaltasEntry.TABLE_NAME ;

    public static class AGC_Prova_FaltasEntry implements BaseColumns {
        public static final String TABLE_NAME = "AGC_Prova_Faltas";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CPF_CANDIDATO = "cpfCandidato";
        public static final String COLUMN_DATA_EXAME = "dataExame";
        public static final String COLUMN_LOCAL_EXAME = "localExame";
        public static final String COLUMN_TIPO_EXAME = "tipoExame";
        public static final String COLUMN_TIPO_FALTA = "tipoFalta";
        public static final String COLUMN_ITEM_LETRA = "itemLetra";
        public static final String COLUMN_QUANTIDADE_FALTAS = "quantidadeDeFaltas";
        public static final String COLUMN_CPF_INCLUSAO = "cpfInclusao";
        public static final String COLUMN_DATAHORA_INCLUSAO = "dataHoraInclusao";
        public static final String COLUMN_OBSERVACOES = "observacoes";
    }
}
