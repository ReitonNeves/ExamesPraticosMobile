package br.gov.ma.detran.examespraticosmobile.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import br.gov.ma.detran.examespraticosmobile.R;

public class MensagemErroUtil {

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

/*
    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    AlertDialog alerta = builder.create();

    LayoutInflater li = LayoutInflater.from(context);

    View view = li.inflate(R.layout.layout_mensagem_erro, null);

    final AlertDialog finalAlerta = alerta;
        view.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
public void onClick(View arg0) {
        finalAlerta.dismiss();
        }
        });

        TextView mTexto = view.findViewById(R.id.txtMensagemErro);
        mTexto.setText(mensagem);

        builder.setTitle("Mensagem");
        builder.setView(view);
        alerta.show();
*/

