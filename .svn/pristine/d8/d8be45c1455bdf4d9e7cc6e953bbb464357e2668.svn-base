package br.gov.ma.detran.examespraticosmobile.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.util.MaskEditUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.ParametrosAcessoUtil;
import br.gov.ma.detran.examespraticosmobile.util.ZeroEsquerdaUtil;

public class SelecionarCandidatoActivity extends AppCompatActivity {

    private AGC_Provas_CandidatosService agcProvasCandidatosService = new AGC_Provas_CandidatosService();

    private EditText mDataExame;
    private EditText mTurma;
    private RadioButton mRadExame2Rodas;
    private RadioButton mRadExame4Rodas;
    private ListView mListaCandidato;
    private ImageButton mBotaoBuscar;
    private TextView mTextView6;

    private View mProgressView;
    //private View mFormView;

    private List<AGC_Prova_Candidato> agcProvaCandidatoParaExaminadorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_candidato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Selecionar Candidato");

        mListaCandidato = findViewById(R.id.listaCandidatos);
        mDataExame = findViewById(R.id.txtDataExame);
        mTurma = findViewById(R.id.txtTurma);
        mRadExame2Rodas = findViewById(R.id.radExame2Rodas);
        mRadExame4Rodas = findViewById(R.id.radExame4Rodas);

        mTextView6 = findViewById(R.id.textView6);
        mTextView6.setVisibility(View.INVISIBLE);

        mListaCandidato = findViewById(R.id.listaCandidatos);
        mListaCandidato.setVisibility(View.INVISIBLE);

         mProgressView = this.findViewById(R.id.progressBarSelecionarCandidato);
        //mFormView = this.findViewById(R.id.formContentImportar);

        checarSeJaHaUmaProvaIniciada();

        mBotaoBuscar_setOnClickListener();

        mListaCandidato_setOnItemClickListener();

        mDataExame_addTextChangedListener();

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

    private void mDataExame_addTextChangedListener(){
        mDataExame = this.findViewById(R.id.txtDataExame);
        mDataExame.addTextChangedListener(MaskEditUtil.mask(mDataExame, MaskEditUtil.FORMAT_DATE));
    }

    private void checarSeJaHaUmaProvaIniciada() {

        try {
            AGC_Prova_Candidato agcProvaCandidato = agcProvasCandidatosService.retornarProvaIniciada(this);
            if (agcProvaCandidato != null) {
                direcionarParaAplicarProva(agcProvaCandidato);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void mBotaoBuscar_setOnClickListener(){

        mBotaoBuscar = this.findViewById(R.id.btnBuscar);

        final AGC_Usuario agcUsuario = ParametrosAcessoUtil.getAgcUsuarioLogado();
        if (agcUsuario != null) {
            if (agcUsuario.getTipoUsuario().equals("G")){
                mBotaoBuscar.setEnabled(false);
            } else {
                mBotaoBuscar.setEnabled(true);
            }
        }

        mBotaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    String turma = mTurma.getText().toString();
                    String dataExame= mDataExame.getText().toString();

                    String tipoExame = "";
                    if (mRadExame2Rodas.isChecked()){
                        tipoExame = "1";
                    } else if (mRadExame4Rodas.isChecked()){
                        tipoExame = "2";
                    }

                    if (turma.isEmpty()) {

                        preencheListaDeCandidatos(dataExame, tipoExame, turma);

                    }
                   mListaCandidato.setVisibility(View.VISIBLE);
                   mTextView6.setVisibility(View.VISIBLE);
                } catch (Exception ex){
                    ex.printStackTrace();
                    mListaCandidato.setVisibility(View.INVISIBLE);
                    mTextView6.setVisibility(View.INVISIBLE);
                    MensagemErroUtil.mostrar(ex.getMessage(), view.getContext());
                }

            }
        });

    }

    private void mListaCandidato_setOnItemClickListener(){

        mListaCandidato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AGC_Prova_Candidato agcProvaCandidatoSelecinoado = agcProvaCandidatoParaExaminadorList.get(position); //  parent.getItemAtPosition(position).toString();

                if (agcProvaCandidatoSelecinoado.getSituacao().equals("S")){

                    String cpfCandidato = ZeroEsquerdaUtil.preencher(11, agcProvaCandidatoSelecinoado.getCpfCandidato());
                    String mensagem = "Deseja iniciar a prova do Candidato " + agcProvaCandidatoSelecinoado.getNome()
                            + ", CPF " + cpfCandidato.substring(0,3) + "."
                            + cpfCandidato.substring(3,6) + "."
                            + cpfCandidato.substring(6,9) + "-"
                            + cpfCandidato.substring(9,11)
                            ;

                    confirmDialog(mensagem, agcProvaCandidatoSelecinoado);

                } else {
                    MensagemErroUtil.mostrar("Situação inválida.", view.getContext());
                }
            }
        });

    }

    private void confirmDialog(String mensagem, final AGC_Prova_Candidato agcProvaCandidatoSelecionado) {

        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setMessage(mensagem)
                .setPositiveButton("Sim",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try {

                            AGC_Prova_Candidato provaIniciada = agcProvasCandidatosService.retornarProvaIniciada(context);
                            if (provaIniciada != null){
                                MensagemErroUtil.mostrar("Já existe uma prova iniciada. Utilize a opção 'Aplicar Prova' novamente na tela anterior.", context);
                            } else {
                                agcProvasCandidatosService.iniciarProvaCandidato(agcProvaCandidatoSelecionado, context);
                                direcionarParaAplicarProva(agcProvaCandidatoSelecionado);
                            }

                        } catch (Exception ex){
                            ex.printStackTrace();
                            MensagemErroUtil.mostrar(ex.getMessage(), context);
                        }
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void direcionarParaAplicarProva(AGC_Prova_Candidato agcProvaCandidatoSelecionado) {
        Intent objIndent = new Intent(getApplicationContext(), AplicarProvaActivity.class);
        objIndent.putExtra("idCandidatoSelecionado", agcProvaCandidatoSelecionado.getId().toString());
        startActivity(objIndent);
        finishAffinity();
    }

    private void preencheListaDeCandidatos(String dataExame, String tipoExame, String turma) throws NegocioException {

        final AGC_Usuario agcUsuario = ParametrosAcessoUtil.getAgcUsuarioLogado();
        if (agcUsuario != null && agcUsuario.getTipoUsuario().equals("E")){

            ArrayAdapter<String> adapter;
            ArrayList<String> listaCandidatos = new ArrayList<>();

            String dataExameFormatada = dataExame.substring(6, 10) + "-"
                                        + dataExame.substring(3,5) + "-"
                                        + dataExame.substring(0,2);

            String cpfFormatado = agcUsuario.getCpfUsuario().replace(".", "").replace("-", "");

            List<AGC_Prova_Candidato> agcProvaCandidatoList = agcProvasCandidatosService.retornarTodosAgendadosParaExaminador(dataExameFormatada, tipoExame, turma, cpfFormatado, this);

            if (agcProvaCandidatoList == null || agcProvaCandidatoList.size() == 0){
                MensagemErroUtil.mostrar("Nenhum registro encontrado.", this);
                listaCandidatos.clear();
            } else {
                for (AGC_Prova_Candidato agcProvaCandidato:agcProvaCandidatoList) {
                    listaCandidatos.add(agcProvaCandidato.getNome() + " - " + agcProvaCandidato.getCpfCandidato());
                }
            }

            this.agcProvaCandidatoParaExaminadorList = agcProvaCandidatoList;

            adapter = new ArrayAdapter<>(SelecionarCandidatoActivity.this,
                    R.layout.simplerow,
                    listaCandidatos);
            mListaCandidato.setAdapter(adapter);

        }

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

           /* mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            //mListaCandidato.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



}
