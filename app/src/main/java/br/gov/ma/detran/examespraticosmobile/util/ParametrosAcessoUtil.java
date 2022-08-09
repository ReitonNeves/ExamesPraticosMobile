package br.gov.ma.detran.examespraticosmobile.util;

import android.app.Application;

import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;

public class ParametrosAcessoUtil extends Application {

    private static AGC_Usuario agcUsuarioLogado;

    public static AGC_Usuario getAgcUsuarioLogado() {
        return agcUsuarioLogado;
    }

    public static void setAgcUsuarioLogado(AGC_Usuario agcUsuarioLogado) {
        ParametrosAcessoUtil.agcUsuarioLogado = agcUsuarioLogado;
    }

}
