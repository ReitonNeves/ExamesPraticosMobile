package br.gov.ma.detran.examespraticosmobile.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MensagemToastUtil {
    public static void mostrar(String mensagem, Context context){
        Toast toast = Toast.makeText(context, mensagem, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
