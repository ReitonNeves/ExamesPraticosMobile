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

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.adapter.ListViewFaltasAdapter;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Falta;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.ListViewFaltas;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Prova_FaltasService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.util.CPFUtil;
import br.gov.ma.detran.examespraticosmobile.util.DataHoraUtil;
import br.gov.ma.detran.examespraticosmobile.util.MaskEditUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemOkUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.ParametrosAcessoUtil;
import br.gov.ma.detran.examespraticosmobile.util.ZeroEsquerdaUtil;
import br.gov.ma.detran.futronictech.FtrScanUsbHostActivity;

public class AplicarProvaActivity extends AppCompatActivity {

    private View mProgressView;

    private AlertDialog alertDialog;

    private ListView mListaFaltas;
    private Button mBotaoLeve;
    private Button mBotaoMedia;
    private Button mBotaoGrave;
    private Button mBotaoEliminatoria;

    private Button mBotaoFecharProva;
    private Button mBotaoAlunoAusente;

    private TextView mTextNomeDoCandidato;
    private TextView mTextCpfDoCandidato;
    private TextView mMensagemConfirmar;
    private TextView mResultado;

    private EditText mPlaca;
    private EditText mObservacao;

    private AGC_Provas_CandidatosService agcProvasCandidatosService = new AGC_Provas_CandidatosService();
    private AGC_Prova_FaltasService agcProvaFaltasService = new AGC_Prova_FaltasService();

    private AGC_Prova_Candidato agcProvaCandidato;

    private String tipoFalta;
    private FloatingActionButton fabDigital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicar_prova);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Aplicar Prova");

        mProgressView = findViewById(R.id.progressBarAplicarProva);
        mListaFaltas = findViewById(R.id.listaFaltas);

        mBotaoLeve = findViewById(R.id.btnLeve);
        mBotaoMedia = findViewById(R.id.btnMedia);
        mBotaoGrave = findViewById(R.id.btnGrave);
        mBotaoEliminatoria = findViewById(R.id.btnEliminatoria);

        mBotaoFecharProva = findViewById(R.id.btnFecharProva);
        mBotaoAlunoAusente = findViewById(R.id.btnAlunoAusente);

        mTextNomeDoCandidato = findViewById(R.id.txtNomeCandidato);
        mTextCpfDoCandidato = findViewById(R.id.txtCpfDoCandidato);
        fabDigital = findViewById(R.id.fab);

        fabDigital.setOnClickListener(view -> {
            if(agcProvaCandidato.getImagemDigital() == null) {
                iniciarScanner();
            }else {
                showDialog("Esse Candidato já tem digital cadastrada. Deseja registrar novamente?");
            }
        });

        try {

            mBotaoLeve_setOnClickListener();
            mBotaoMedia_setOnClickListener();
            mBotaoGrave_setOnClickListener();
            mBotaoEliminatoria_setOnClickListener();

            mBotaoFecharProva_setOnClickListener();
            mBotaoAlunoAusente_setOnClickListener();

            String idCandidatoSelecionado = "";
            Intent intent = getIntent();
            if (intent.getStringExtra("idCandidatoSelecionado") != null) {
                idCandidatoSelecionado = intent.getStringExtra("idCandidatoSelecionado");
            }

            agcProvaCandidato = agcProvasCandidatosService.retornarPorID(idCandidatoSelecionado, this);
            if (agcProvaCandidato == null){
                MensagemErroUtil.mostrar("Registrado não encontrado.", this);
            } else if (!agcProvaCandidato.getSituacao().equals("I")) {
                MensagemErroUtil.mostrar("Situação inválida.", this);
            } else {

                mTextNomeDoCandidato.setText(agcProvaCandidato.getNome());
                mTextCpfDoCandidato.setText(CPFUtil.formatar(ZeroEsquerdaUtil.preencher(11, agcProvaCandidato.getCpfCandidato())));

                preencherListaDeFaltas(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getTipoExame(), "L");

                preencherHeader_AplicarProva(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(), agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), "L");
                preencherHeader_AplicarProva(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(), agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), "M");
                preencherHeader_AplicarProva(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(), agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), "G");
                preencherHeader_AplicarProva(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(), agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), "E");

            }

        } catch (Exception ex){
            ex.printStackTrace();
            MensagemErroUtil.mostrar(ex.getMessage(), this);
        }

    }

    private void iniciarScanner() {
        Intent intent = new Intent(AplicarProvaActivity.this, FtrScanUsbHostActivity.class);
        intent.putExtra("candidato", agcProvaCandidato);
        //intent.putExtra("digital", agcProvaCandidato.getImagemDigital());
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            agcProvaCandidato.setImagemDigital(data.getByteArrayExtra("dig"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, HomeActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, HomeActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void mBotaoFecharProva_setOnClickListener(){
        mBotaoFecharProva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(agcProvaCandidato.getImagemDigital() != null){
                        confirmarDialogResultadoProva();
                    }else {
                        MensagemErroUtil.mostrar("Candidato sem biometria capturada.", view.getContext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MensagemErroUtil.mostrar(e.getMessage(), view.getContext());
                }
            }
        });
    }

    private void mBotaoAlunoAusente_setOnClickListener(){

        mBotaoAlunoAusente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    confirmarDialogAlunoAusente();
                } catch (Exception e) {
                    e.printStackTrace();
                    MensagemErroUtil.mostrar(e.getMessage(), view.getContext());
                }
            }
        });

    }

    private void mBotaoLeve_setOnClickListener(){
        mBotaoLeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    preencherListaDeFaltas(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getTipoExame(), "L");
                    setarCoresBotao();
                    mBotaoLeve.setBackgroundColor(getResources().getColor(R.color.colorHoloBlueLight));
                } catch (Exception e) {
                    e.printStackTrace();
                    MensagemErroUtil.mostrar(e.getMessage(), view.getContext());
                }
            }
        });
    }

    private void mBotaoMedia_setOnClickListener(){
        mBotaoMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    preencherListaDeFaltas(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getTipoExame(), "M");
                    setarCoresBotao();
                    mBotaoMedia.setBackgroundColor(getResources().getColor(R.color.colorHoloBlueLight));
                } catch (Exception e) {
                    e.printStackTrace();
                    MensagemErroUtil.mostrar(e.getMessage(), view.getContext());
                }
            }
        });
    }

    private void mBotaoGrave_setOnClickListener(){
        mBotaoGrave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    preencherListaDeFaltas(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getTipoExame(), "G");
                    setarCoresBotao();
                    mBotaoGrave.setBackgroundColor(getResources().getColor(R.color.colorHoloBlueLight));
                } catch (Exception e) {
                    e.printStackTrace();
                    MensagemErroUtil.mostrar(e.getMessage(), view.getContext());
                }
            }
        });
    }

    private void mBotaoEliminatoria_setOnClickListener(){
        mBotaoEliminatoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    preencherListaDeFaltas(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getTipoExame(), "E");
                    setarCoresBotao();
                    mBotaoEliminatoria.setBackgroundColor(getResources().getColor(R.color.colorHoloBlueLight));
                } catch (Exception e) {
                    e.printStackTrace();
                    MensagemErroUtil.mostrar(e.getMessage(), view.getContext());
                }
            }
        });
    }

    private void setarCoresBotao(){
        mBotaoLeve.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        mBotaoMedia.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        mBotaoGrave.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        mBotaoEliminatoria.setBackgroundColor(getResources().getColor(R.color.colorBlue));
    }

    private void preencherListaDeFaltas(String cpfCandidato, String tipoExame, String tipoFalta) throws NegocioException {

        List<ListViewFaltas> lista;
        lista = agcProvaFaltasService.retornarQuestoesParaCandidatoETipoDeExame(cpfCandidato, tipoExame, tipoFalta, this);

        ListViewFaltasAdapter adapter = new ListViewFaltasAdapter(this, lista);
        mListaFaltas.setAdapter(adapter);

        this.tipoFalta = tipoFalta;

    }

    private void preencherHeader_AplicarProva(String cpfCandidato, String dataExame, String localExame, String tipoExame, String tipoFalta) throws NegocioException {

        List<AGC_Prova_Falta> agcProvaFaltaList = agcProvaFaltasService.retornarFaltasParaCandidatoETipoDeExame(cpfCandidato, dataExame, localExame, tipoExame, tipoFalta, this);

        if (agcProvaFaltaList != null){
            for (AGC_Prova_Falta agcProvaFalta : agcProvaFaltaList) {
                Integer totalFaltas = 0;
                totalFaltas = totalFaltas + agcProvaFalta.getQuantidadeDeFaltas();
                if(tipoFalta.equals("L")){

                    String texto = getResources().getString(R.string.textoTabLeve);
                    if (totalFaltas > 0) {
                        texto = texto + "(" + totalFaltas + ")";
                    }
                    mBotaoLeve.setText(texto);

                } else if(tipoFalta.equals("M")){

                    String texto = getResources().getString(R.string.textoTabMedia);
                    if (totalFaltas > 0) {
                        texto = texto + "(" + totalFaltas + ")";
                    }
                    mBotaoMedia.setText(texto);

                } if(tipoFalta.equals("G")){

                    String texto = getResources().getString(R.string.textoTabGrave);
                    if (totalFaltas > 0) {
                        texto = texto + "(" + totalFaltas + ")";
                    }
                    mBotaoGrave.setText(texto);

                } if(tipoFalta.equals("E")){
                    String texto = getResources().getString(R.string.textoTabEliminatoria);
                    if (totalFaltas > 0) {
                        texto = texto + "(" + totalFaltas + ")";
                    }
                    mBotaoEliminatoria.setText(texto);
                }
            }
        } else {
            if(tipoFalta.equals("L")){
                String texto = getResources().getString(R.string.textoTabLeve);
                mBotaoLeve.setText(texto);
            } else if(tipoFalta.equals("M")){
                String texto = getResources().getString(R.string.textoTabMedia);
                mBotaoMedia.setText(texto);
            } if(tipoFalta.equals("G")){
                String texto = getResources().getString(R.string.textoTabGrave);
                mBotaoGrave.setText(texto);
            } if(tipoFalta.equals("E")){
                String texto = getResources().getString(R.string.textoTabEliminatoria);
                mBotaoEliminatoria.setText(texto);
            }
        }

    }

    public void addFalta(View v)  {

        try {

            LinearLayout parentRow = (LinearLayout) v.getParent();
            TextView tx = (TextView) parentRow.getChildAt(0);
            String questao = tx.getText().toString();
            String letra = questao.substring(0, questao.indexOf(")"));

            if (!agcProvaCandidato.getSituacao().equals("I")){
                MensagemErroUtil.mostrar("Situação inválida. Retorne para a seleção de candidato.", this);
            } else {
                AGC_Prova_Falta agcProvaFalta = agcProvaFaltasService.retornarQuestaoDoCandidato(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(),
                        agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), tipoFalta, letra, this);

                if (agcProvaFalta == null) {

                    AGC_Prova_Falta agcProvaFaltaNovo = new AGC_Prova_Falta();
                    agcProvaFaltaNovo.setCpfCandidato(agcProvaCandidato.getCpfCandidato());
                    agcProvaFaltaNovo.setDataExame(agcProvaCandidato.getDataExame());
                    agcProvaFaltaNovo.setLocalExame(agcProvaCandidato.getLocalExame());
                    agcProvaFaltaNovo.setTipoExame(agcProvaCandidato.getTipoExame());
                    agcProvaFaltaNovo.setTipoFalta(tipoFalta);
                    agcProvaFaltaNovo.setItemLetra(letra);
                    agcProvaFaltaNovo.setQuantidadeDeFaltas(1);
                    agcProvaFaltaNovo.setCpfInclusao(ParametrosAcessoUtil.getAgcUsuarioLogado().getCpfUsuario());
                    agcProvaFaltaNovo.setDataHoraInclusao(DataHoraUtil.retornarDataHoraAtual_yyyyMMdd_HHmmss());

                    agcProvaFaltasService.inserir(agcProvaFaltaNovo, this);

                } else {

                    agcProvaFalta.setCpfInclusao(ParametrosAcessoUtil.getAgcUsuarioLogado().getCpfUsuario());
                    agcProvaFalta.setDataHoraInclusao(DataHoraUtil.retornarDataHoraAtual_yyyyMMdd_HHmmss());
                    agcProvaFalta.setQuantidadeDeFaltas(agcProvaFalta.getQuantidadeDeFaltas() + 1);

                    agcProvaFaltasService.atualizarQuantidadeDeFaltas(agcProvaFalta, this);

                }

                preencherListaDeFaltas(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getTipoExame(), tipoFalta);
                preencherHeader_AplicarProva(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(), agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), tipoFalta);
            }

        } catch (Exception ex){
            ex.printStackTrace();
            MensagemErroUtil.mostrar(ex.getMessage(), this);
        }

    }

    public void removeFalta(View v) {

        try {

            LinearLayout parentRow = (LinearLayout) v.getParent();
            TextView tx = (TextView) parentRow.getChildAt(0);
            String questao = tx.getText().toString();
            String letra = questao.substring(0, questao.indexOf(")"));

            if (!agcProvaCandidato.getSituacao().equals("I")){
                MensagemErroUtil.mostrar("Situação inválida. Retorne para a seleção de candidato.", this);
            } else {

                AGC_Prova_Falta agcProvaFalta = agcProvaFaltasService.retornarQuestaoDoCandidato(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(),
                        agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), tipoFalta, letra, this);

                if (agcProvaFalta != null && agcProvaFalta.getQuantidadeDeFaltas() != null && agcProvaFalta.getQuantidadeDeFaltas() > 0){

                    agcProvaFalta.setQuantidadeDeFaltas(agcProvaFalta.getQuantidadeDeFaltas() - 1);

                    if (agcProvaFalta.getQuantidadeDeFaltas() == 0){
                        agcProvaFaltasService.removerProvaFalta(agcProvaFalta, this);
                    } else {
                        agcProvaFaltasService.atualizarQuantidadeDeFaltas(agcProvaFalta, this);
                    }

                }

                preencherListaDeFaltas(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getTipoExame(), tipoFalta);
                preencherHeader_AplicarProva(agcProvaCandidato.getCpfCandidato(), agcProvaCandidato.getDataExame(), agcProvaCandidato.getLocalExame(), agcProvaCandidato.getTipoExame(), tipoFalta);

            }

        } catch (Exception ex){
            ex.printStackTrace();
            MensagemErroUtil.mostrar(ex.getMessage(), this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        final AGC_Usuario agcUsuario = ParametrosAcessoUtil.getAgcUsuarioLogado();
        if (agcUsuario == null) {
            direcionarParaLogin();
        }
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
            /*mFormView.setVisibility(show ? View.GONE : View.VISIBLE);*/
        }
    }

    private void confirmarDialogAlunoAusente() {

        final Context context = this;

        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.dialog_aluno_ausente, null);

        view.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try {

                    agcProvaCandidato.setResultado("3");
                    agcProvaCandidato.setHoraFimExame(DataHoraUtil.retornarHoraAtual_HHmmss());
                    agcProvasCandidatosService.alterarSituacaoDoCandidatoParaPendente(agcProvaCandidato, context);
                    alertDialog.dismiss();

                    MensagemOkUtil.mostrar("Aluno Ausente confirmado.", context);

                } catch (NegocioException e) {
                    e.printStackTrace();
                    alertDialog.dismiss();
                    MensagemErroUtil.mostrar(e.getMessage(), context);
                }

            }
        });

        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alertDialog.dismiss();
            }
        });

        String mensagemConfirmar = "Você deseja confirmar "
                + agcProvaCandidato.getNome()
                + ", CPF: " + agcProvaCandidato.getCpfCandidato() + " como ausente? \n "+
                "Se sim, informe os dados abaixo e clique em Confirmar. Caso contrário, clique em cancelar."
                ;

        mMensagemConfirmar = view.findViewById(R.id.txtMensagemConfirmar);
        mMensagemConfirmar.setText(mensagemConfirmar);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Confirmar aluno ausente");
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.show();

    }

    private void confirmarDialogResultadoProva() throws NegocioException {

        final Context context = this;

        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.dialog_confirmar_resultado, null);

        final String resultado = agcProvasCandidatosService.retornarResultadoDoCandidato(agcProvaCandidato, this);

        mPlaca = view.findViewById(R.id.txtPlacaVeiculo);
        mPlaca.addTextChangedListener(MaskEditUtil.mask(mPlaca, MaskEditUtil.FORMAT_PLACA));

        mObservacao = view.findViewById(R.id.txtObservacao);

        view.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try {

                    agcProvaCandidato.setPlaca(mPlaca.getText().toString());
                    agcProvaCandidato.setObservacoes(mObservacao.getText().toString());

                    if (resultado.equals("Aprovado")){
                        agcProvaCandidato.setResultado("1");
                    } else if (resultado.equals("Reprovado")){
                        agcProvaCandidato.setResultado("2");
                    }

                    agcProvaCandidato.setHoraFimExame(DataHoraUtil.retornarHoraAtual_HHmmss());

                    agcProvasCandidatosService.alterarSituacaoDoCandidatoParaPendente(agcProvaCandidato, context);

                    alertDialog.dismiss();

                    MensagemOkUtil.mostrar("Aluno " + resultado +  ".", context);

                } catch (Exception e) {
                    e.printStackTrace();
                    alertDialog.dismiss();
                    MensagemErroUtil.mostrar(e.getMessage(), context);
                }

            }
        });

        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btnEnviar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alertDialog.dismiss();
            }
        });

        String tituloResultado = "Resultado: " + resultado;
        mResultado = view.findViewById(R.id.txtResultado);
        mResultado.setText(tituloResultado);
        if (resultado.equals("Aprovado")) {
            mResultado.setTextColor(getResources().getColor(R.color.colorHoloGreenLight));
        } else if (resultado.equals("Reprovado")) {
            mResultado.setTextColor(getResources().getColor(R.color.colorHoloRedLight));
        }

        String mensagemConfirmar = "Você deseja confirmar "
                + agcProvaCandidato.getNome()
                + ", CPF: " + agcProvaCandidato.getCpfCandidato() + " como " + resultado + "? \n " +
                "Se sim, informe os dados abaixo e clique em Confirmar. Caso contrário, clique em cancelar.";

        mMensagemConfirmar = view.findViewById(R.id.txtMensagemConfirmar);

        mMensagemConfirmar.setText(mensagemConfirmar);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Confirmar aluno ausente");
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.show();

    }

    private void direcionarParaLogin(){
        Intent objIndent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(objIndent);
        finishAffinity();
    }

    private void showDialog(String mensagem) {

        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(mensagem)
                .setPositiveButton("Sim", (dialog, id) -> {

                    try {
                        iniciarScanner();
                    } catch (Exception ex){
                        ex.printStackTrace();
                        MensagemErroUtil.mostrar(ex.getMessage(), context);
                    }
                })
                .setNegativeButton("Não", (dialog, id) -> dialog.cancel())
                .show();
    }

}
