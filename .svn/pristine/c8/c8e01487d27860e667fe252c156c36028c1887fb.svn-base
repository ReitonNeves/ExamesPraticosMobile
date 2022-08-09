package br.gov.ma.detran.examespraticosmobile.util;

import android.app.AlertDialog;
import android.content.Context;

public class MensagemOkUtil {

    public static void mostrar(String mensagem, Context context) {

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(mensagem);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
