package br.gov.ma.detran.examespraticosmobile.util;

import android.content.Context;
import android.content.Intent;

import br.gov.ma.detran.examespraticosmobile.views.LoginActivity;

public class LogoutUtil {
    public static void sair(Context context){
        ParametrosAcessoUtil parametrosAcessoUtil = ((ParametrosAcessoUtil)context);
        parametrosAcessoUtil.setAgcUsuarioLogado(null);
        direcionarParaLogin(context);
    }

    private static void direcionarParaLogin(Context context){
        Intent objIndent = new Intent(context, LoginActivity.class);
        context.startActivity(objIndent);
    }
}
