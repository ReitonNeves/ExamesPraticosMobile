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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_UsuariosService;
import br.gov.ma.detran.examespraticosmobile.util.CPFUtil;
import br.gov.ma.detran.examespraticosmobile.util.ColorStatusBarUtil;
import br.gov.ma.detran.examespraticosmobile.util.DataHoraUtil;
import br.gov.ma.detran.examespraticosmobile.util.MaskEditUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemOkUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.ParametrosAcessoUtil;
import br.gov.ma.detran.examespraticosmobile.util.ZeroEsquerdaUtil;

public class SelecionarCandidatoActivity extends AppCompatActivity {

    private AGC_Provas_CandidatosService agcProvasCandidatosService = new AGC_Provas_CandidatosService();
    private AGC_UsuariosService agcUsuariosService = new AGC_UsuariosService();
    private EditText mDataExame;
    private EditText mTurma;
    private RadioButton mRadExame2Rodas;
    private RadioButton mRadExame4Rodas;
    private ListView mListaCandidato;
    private ImageButton mBotaoBuscar;
    private TextView mTextView6;
    AlertDialog alertDialog;

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

        ColorStatusBarUtil.setColorStatusBar(this);

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
        /*if (agcUsuario != null) {
            if (agcUsuario.getTipoUsuario().equals("G")){
                mBotaoBuscar.setEnabled(false);
            } else {
                mBotaoBuscar.setEnabled(true);
            }
        }*/

        mBotaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String turma = mTurma.getText().toString();
                    String dataExame = mDataExame.getText().toString();

                    String tipoExame = "";
                    if (mRadExame2Rodas.isChecked()) {
                        tipoExame = "1";
                    } else if (mRadExame4Rodas.isChecked()) {
                        tipoExame = "2";
                    }

                    preencheListaDeCandidatos(dataExame, tipoExame, turma);

                    mListaCandidato.setVisibility(View.VISIBLE);
                    mTextView6.setVisibility(View.VISIBLE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    mListaCandidato.setVisibility(View.INVISIBLE);
                    mTextView6.setVisibility(View.INVISIBLE);
                    MensagemErroUtil.mostrar(ex.getMessage(), SelecionarCandidatoActivity.this);
                }
            }
        });
    }

    private void mListaCandidato_setOnItemClickListener(){
        final AGC_Usuario agcUsuario = ParametrosAcessoUtil.getAgcUsuarioLogado();

        mListaCandidato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AGC_Prova_Candidato agcProvaCandidatoSelecionado = agcProvaCandidatoParaExaminadorList.get(position); //  parent.getItemAtPosition(position).toString();

                if (agcProvaCandidatoSelecionado.getSituacao().equals("S") && agcUsuario.getTipoUsuario().equals("E")){
                    String cpfCandidato = ZeroEsquerdaUtil.preencher(11, agcProvaCandidatoSelecionado.getCpfCandidato());
                    String mensagem = "Deseja iniciar a prova do Candidato "
                            + agcProvaCandidatoSelecionado.getNome()
                            + ", CPF " + cpfCandidato.substring(0,3) + "."
                            + cpfCandidato.substring(3,6) + "."
                            + cpfCandidato.substring(6,9) + "-"
                            + cpfCandidato.substring(9,11);

                    confirmDialogIniciarExame(mensagem, agcProvaCandidatoSelecionado);

                } else if(agcProvaCandidatoSelecionado.getSituacao().equals("S")){//Refatorar
                    String cpfCandidato = ZeroEsquerdaUtil.preencher(11, agcProvaCandidatoSelecionado.getCpfCandidato());
                    String mensagem = "Deseja registrar a presenca do Candidato "
                            + agcProvaCandidatoSelecionado.getNome()
                            + ", CPF " + cpfCandidato.substring(0,3) + "."
                            + cpfCandidato.substring(3,6) + "."
                            + cpfCandidato.substring(6,9) + "-"
                            + cpfCandidato.substring(9,11);

                    confirmDialogRegistrarPresnca(mensagem, agcProvaCandidatoSelecionado);
                } else {
                    MensagemErroUtil.mostrar("Situação inválida.", view.getContext());
                }
            }
        });
    }

    private void confirmDialogIniciarExame(String mensagem, final AGC_Prova_Candidato agcProvaCandidatoSelecionado) {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(mensagem)
                .setPositiveButton("Sim",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            showDialogPreExame(agcProvaCandidatoSelecionado);
                        } catch (NegocioException e) {
                            e.printStackTrace();
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

    private void confirmDialogRegistrarPresnca(String mensagem, final AGC_Prova_Candidato agcProvaCandidatoSelecionado) {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(mensagem)
                .setPositiveButton("Sim",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            showDialogRegistroDePresenca(agcProvaCandidatoSelecionado);
                        } catch (NegocioException e) {
                            e.printStackTrace();
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

    public void showDialogPreExame( AGC_Prova_Candidato agcProvaCandidatoSelecionado) throws NegocioException {
        final Context context = this;
        final AGC_Usuario agcUsuario = ParametrosAcessoUtil.getAgcUsuarioLogado();
        //final AGC_Prova_Candidato agcProvaCandidato = agcProvasCandidatosService.retornarPorID(String.valueOf(agcProvaCandidatoSelecionado.getId()),this);

        if (!agcUsuario.getTipoUsuario().equals("E")){
            MensagemErroUtil.mostrar("Situação inválida. O usuário logado não tem permissão para aplicar prova.", this);
        } else {

        }

        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.dialog_pre_exame, null);

        EditText mPlaca = view.findViewById(R.id.txtPlacaVeiculo);
        mPlaca.addTextChangedListener(MaskEditUtil.mask(mPlaca, MaskEditUtil.FORMAT_PLACA));

        EditText mDataValLadv = view.findViewById(R.id.txtValidadeLadv);
        mDataValLadv.addTextChangedListener(MaskEditUtil.mask(mDataValLadv, MaskEditUtil.FORMAT_DATE));

        EditText mTextCpfExaminador1 = view.findViewById(R.id.txtCpfExaminador1);
        mTextCpfExaminador1.addTextChangedListener(MaskEditUtil.mask(mTextCpfExaminador1, MaskEditUtil.FORMAT_CPF));
        mTextCpfExaminador1.setText(agcUsuario.getCpfUsuario());
        EditText mTextCpfExaminador2 = view.findViewById(R.id.txtCpfExaminador2);
        mTextCpfExaminador2.addTextChangedListener(MaskEditUtil.mask(mTextCpfExaminador2, MaskEditUtil.FORMAT_CPF));

        view.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String placa = mPlaca.getText().toString();
                String dataValLadv = mDataValLadv.getText().toString();
                String cpf1 = mTextCpfExaminador1.getText().toString();
                String cpf2 = mTextCpfExaminador2.getText().toString();

                if(placa.isEmpty() || cpf1.isEmpty() || cpf2.isEmpty()){
                    MensagemOkUtil.mostrar("Os campos são de preenchimento obrigatório.", context);
                } else{
                    try {
                        agcProvaCandidatoSelecionado.setPlaca(placa);
                        agcProvaCandidatoSelecionado.setValidadeLadv(DataHoraUtil.formataData(dataValLadv));
                        AGC_Usuario usuario = agcUsuariosService.retornarPorCpf(CPFUtil.formatarSemCaracteres(cpf2), SelecionarCandidatoActivity.this);
                        if(Objects.isNull(usuario)){
                            MensagemOkUtil.mostrar("Examinador não encontrado para o cpf informado.", context);
                        } else if(agcProvaCandidatoSelecionado.getSituacao().equals("S")) {
                            AGC_Prova_Candidato provaIniciada = agcProvasCandidatosService.retornarProvaIniciada(context);
                            if (provaIniciada != null) {
                                MensagemErroUtil.mostrar("Já existe uma prova iniciada. Utilize a opção 'Aplicar Prova' novamente na tela anterior.", context);
                            } else {
                                agcProvaCandidatoSelecionado.setCpfExaminador2(CPFUtil.formatarSemCaracteres(cpf2));
                                agcProvasCandidatosService.iniciarProvaCandidato(agcProvaCandidatoSelecionado, context);
                                direcionarParaAplicarProva(agcProvaCandidatoSelecionado);
                            }
                        } else if (agcProvaCandidatoSelecionado.getSituacao().equals("S")){
                            agcProvaCandidatoSelecionado.setHorarioRegistroPresenca(DataHoraUtil.retornarHoraAtual_HHmmss());
                            agcProvasCandidatosService.alterarSituacaoDoCandidatoParaDisponivel(agcProvaCandidatoSelecionado, context);
                            mBotaoBuscar.callOnClick();
                        }
                    } catch (Exception ex){
                        ex.printStackTrace();
                        MensagemErroUtil.mostrar(ex.getMessage(), context);
                    }
                    alertDialog.dismiss();
                }
            }
        });

        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alertDialog.dismiss();
            }
        });

        String mensagemConfirmar = " Pré-requisitos para INICIAR o exame: placa veículo, data de validade da LADV, digital do aluno, credenciais dos examinadores.";
        TextView mMensagemConfirmar = view.findViewById(R.id.txtMensagemConfirmar);
        mMensagemConfirmar.setText(mensagemConfirmar);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( agcProvaCandidatoSelecionado.getNome() + ", CPF : " + CPFUtil.formatar(ZeroEsquerdaUtil.preencher(11, agcProvaCandidatoSelecionado.getCpfCandidato())));
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void showDialogRegistroDePresenca(AGC_Prova_Candidato agcProvaCandidatoSelecionado) throws NegocioException{
        final Context context = this;

        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.dialog_registro_presenca, null);

        EditText mPlaca = view.findViewById(R.id.txtPlacaVeiculo);
        mPlaca.addTextChangedListener(MaskEditUtil.mask(mPlaca, MaskEditUtil.FORMAT_PLACA));

        EditText mDataValLadv = view.findViewById(R.id.txtValidadeLadv);
        mDataValLadv.addTextChangedListener(MaskEditUtil.mask(mDataValLadv, MaskEditUtil.FORMAT_DATE));

        String mensagemConfirmar = " Informe a placa do veículo de exame e a data de validade da LADV.";
        TextView mMensagemConfirmar = view.findViewById(R.id.txtMensagemConfirmar);
        mMensagemConfirmar.setText(mensagemConfirmar);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( agcProvaCandidatoSelecionado.getNome() + ", CPF : " + CPFUtil.formatar(ZeroEsquerdaUtil.preencher(11, agcProvaCandidatoSelecionado.getCpfCandidato())));
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        view.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String placa = mPlaca.getText().toString();
                String dataValLadv = mDataValLadv.getText().toString();

                if(placa.isEmpty() || dataValLadv.isEmpty()){
                    MensagemOkUtil.mostrar("Os campos são de preenchimento obrigatório.", context);
                } else{
                    agcProvaCandidatoSelecionado.setPlaca(placa);
                    agcProvaCandidatoSelecionado.setValidadeLadv(dataValLadv);
                    try {
                        if (agcProvaCandidatoSelecionado.getSituacao().equals("S")){
                            agcProvaCandidatoSelecionado.setHorarioRegistroPresenca(DataHoraUtil.retornarHoraAtual_HHmmss());
                            agcProvasCandidatosService.alterarSituacaoDoCandidatoParaDisponivel(agcProvaCandidatoSelecionado, context);
                            mBotaoBuscar.callOnClick();
                        }
                    } catch (Exception ex){
                        ex.printStackTrace();
                        MensagemErroUtil.mostrar(ex.getMessage(), context);
                    }
                    alertDialog.dismiss();
                }
            }
        });

        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alertDialog.dismiss();
            }
        });

    }

    private void direcionarParaAplicarProva(AGC_Prova_Candidato agcProvaCandidatoSelecionado) {
        Intent objIndent = new Intent(getApplicationContext(), AplicarProvaActivity.class);
        objIndent.putExtra("idCandidatoSelecionado", agcProvaCandidatoSelecionado.getId().toString());
        startActivity(objIndent);
        finishAffinity();
    }

    private void preencheListaDeCandidatos(String dataExame, String tipoExame, String turma) throws NegocioException {
        final AGC_Usuario agcUsuario = ParametrosAcessoUtil.getAgcUsuarioLogado();

        if (agcUsuario != null ){//&& agcUsuario.getTipoUsuario().equals("E")
            ArrayList<String> listaCandidatos = new ArrayList<>();
            String dataExameFormatada = DataHoraUtil.formataData(dataExame);
            String cpfFormatado = agcUsuario.getCpfUsuario().replace(".", "").replace("-", "");

            //Lógica para retorna uma lista para registrar presença ou para aplicar o exame.
            List<AGC_Prova_Candidato> agcProvaCandidatoList;
            if (this.getIntent().getBooleanExtra("registrarPresenca", false)){
                agcProvaCandidatoList = agcProvasCandidatosService.listarTodos(tipoExame, cpfFormatado,this);
            } else{
                agcProvaCandidatoList = agcProvasCandidatosService.retornarTodosAgendadosParaExaminador(dataExameFormatada, tipoExame, turma, cpfFormatado, this);
            }

            if (agcProvaCandidatoList == null || agcProvaCandidatoList.size() == 0){
                MensagemErroUtil.mostrar("Nenhum registro encontrado.", this);
                listaCandidatos.clear();
            } else {
                for (AGC_Prova_Candidato agcProvaCandidato:agcProvaCandidatoList) {
                    String status = agcProvaCandidato.getSituacao().equals("D")?"(Disponível)":"";
                    listaCandidatos.add(agcProvaCandidato.getNome() + " - "
                            + agcProvaCandidato.getCpfCandidato()+ " ("+ DataHoraUtil.parseLocalTime(agcProvaCandidato.getTurma())+")"
                            + "\t "+ status);
                }
            }

            this.agcProvaCandidatoParaExaminadorList = agcProvaCandidatoList;

            ArrayAdapter<String> adapter;
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
