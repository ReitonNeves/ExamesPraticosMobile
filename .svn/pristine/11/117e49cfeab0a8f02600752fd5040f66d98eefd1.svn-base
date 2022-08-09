package br.gov.ma.detran.examespraticosmobile.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.adapter.ListViewFecharAdapter;
import br.gov.ma.detran.examespraticosmobile.dao.rest.AGC_Prova_FaltaRest;
import br.gov.ma.detran.examespraticosmobile.dao.rest.AGC_Provas_CandidatosRest;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Falta;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ListViewFechar;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Prova_FaltasService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.util.DataHoraUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemOkUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.ParametrosAcessoUtil;

public class FecharAgendaActivity extends AppCompatActivity {

    private AGC_Provas_CandidatosService agcProvasCandidatosService = new AGC_Provas_CandidatosService();
    AGC_Prova_FaltasService agcProvaFaltasService = new AGC_Prova_FaltasService();

    AGC_Prova_FaltaRest agcProvaFaltaRest = new AGC_Prova_FaltaRest();
    AGC_Provas_CandidatosRest agcProvasCandidatosRest = new AGC_Provas_CandidatosRest();

    private ListView mLista;
    private List<ListViewFechar> listViewFechar = new ArrayList<>();

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechar_agenda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Fechar Agenda");

        mProgressView = this.findViewById(R.id.progressBarFecharAgenda);

        mLista = findViewById(R.id.listaAgendasFechar);

        mFecharTodos_setOnClickListener();

        try {
            preencherLista();
        } catch (Exception ex){
            ex.printStackTrace();
            MensagemErroUtil.mostrar(ex.getMessage(), this);
        }

    }

    private void mFecharTodos_setOnClickListener(){

        Button mFecharTodos = findViewById(R.id.btnFecharTodos);
        mFecharTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<ListViewFechar> agcProvaCandidatoList = agcProvasCandidatosService.retornarTodosAgendadosParaFechar(view.getContext());
                    for (ListViewFechar l:agcProvaCandidatoList) {
                        try {
                            fecharAgendaDoCandidato(l.getAgcProvaCandidato());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    preencherLista();
                } catch (NegocioException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fecharAgenda(View v){

        LinearLayout parentRow = (LinearLayout) v.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);

        try {
            AGC_Prova_Candidato agcProvaCandidato = this.listViewFechar.get(position).getAgcProvaCandidato();
            fecharAgendaDoCandidato(agcProvaCandidato);
            preencherLista();
            MensagemOkUtil.mostrar("Agenda fechada com sucesso.", v.getContext());
        } catch (Exception e) {
            e.printStackTrace();
            MensagemErroUtil.mostrar(e.getMessage(), v.getContext());
        }
    }

    private void fecharAgendaDoCandidato(AGC_Prova_Candidato agcProvaCandidato) throws NegocioException, UnsupportedEncodingException {

        agcProvaCandidato.setCpfEnvioExame(ParametrosAcessoUtil.getAgcUsuarioLogado().getCpfUsuario());
        agcProvaCandidato.setDataHoraEnvioExame(DataHoraUtil.retornarDataHoraAtual_yyyyMMdd_HHmmss());
        //agcProvaCandidato.setSituacao("F");

        if (agcProvaCandidato.getPlaca() == null || agcProvaCandidato.getPlaca().isEmpty() || agcProvaCandidato.getPlaca().equals("null")){
            agcProvaCandidato.setPlaca(" ");
        }

        if (agcProvaCandidato.getObservacoes() == null || agcProvaCandidato.getObservacoes().isEmpty() || agcProvaCandidato.getObservacoes().equals("null")){
            agcProvaCandidato.setObservacoes(" ");
        }

        agcProvasCandidatosRest.alterarSituacaoDoCandidatoParaFechadoNoRestService(agcProvaCandidato);
        //TODO: Validar se restante do processo irá falhar

        agcProvaFaltaRest.remover(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(), agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame());

        List<AGC_Prova_Falta> agcProvaFaltaList = agcProvaFaltasService.retornarFaltasParaCandidatoETipoDeExame(
                agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(),
                agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), this);

        if (agcProvaFaltaList != null){
            for (AGC_Prova_Falta a : agcProvaFaltaList) {
                agcProvaFaltaRest.inserir(a);
            }
        }

        agcProvasCandidatosService.alterarSituacaoDoCandidatoParaFechado(agcProvaCandidato, this);

    }

    private void preencherLista() throws NegocioException {

        List<ListViewFechar> agcProvaCandidatoList = agcProvasCandidatosService.retornarTodosAgendadosParaFechar(this);

        ArrayList<ListViewFechar> lista = new ArrayList<>();

        if (agcProvaCandidatoList == null || agcProvaCandidatoList.size() == 0){
            lista.clear();
            MensagemErroUtil.mostrar("Nenhum registro encontrado.", this);
        } else {
            for (ListViewFechar l:agcProvaCandidatoList) {
                lista.add(l);
            }

            this.listViewFechar = agcProvaCandidatoList;
        }

        ListViewFecharAdapter adapter = new ListViewFecharAdapter(this, lista);
        mLista.setAdapter(adapter);

    }

    private void direcionarParaLogin(){
        Intent objIndent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(objIndent);
        finishAffinity();
    }

    private void direcionarParaHome(){
        Intent objIndent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(objIndent);
        finishAffinity();
    }

    @Override
    public void onResume() {
        super.onResume();

        final AGC_Usuario agcUsuario = ParametrosAcessoUtil.getAgcUsuarioLogado();
        if (agcUsuario == null) {
            direcionarParaLogin();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                direcionarParaHome();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        direcionarParaHome();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            /*mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });*/

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
