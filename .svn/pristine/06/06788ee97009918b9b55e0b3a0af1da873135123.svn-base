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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.List;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_LocalDeProva;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_LocalDeProvaService;
import br.gov.ma.detran.examespraticosmobile.sincronizacao.AGC_Provas_CandidatosSinc;
import br.gov.ma.detran.examespraticosmobile.util.MaskEditUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemOkUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.ParametrosAcessoUtil;

public class ImportarActivity extends AppCompatActivity {

    private EditText mTextDataExameInicial;
    private EditText mTextDataExameFinal;
    private EditText mTextCpfExaminador1;
    private EditText mTextCpfExaminador2;
    private RadioButton mExame2Rodas;
    private RadioButton mExame4Rodas;
    private EditText mLocalExame;

    private Button mBotaoImportar;

    private View mProgressView;
    private View mFormView;

    private AGC_LocalDeProvaService agcLocalDeProvaService = new AGC_LocalDeProvaService();
    private AGC_Provas_CandidatosSinc agcProvasCandidatosSinc = new AGC_Provas_CandidatosSinc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Importar Agenda de Exames");

        mProgressView = this.findViewById(R.id.progressBarImportar);
        mFormView = this.findViewById(R.id.formContentImportar);

        try {

            mTextDataExameInicial_addTextChangedListener();
            mTextDataExameFinal_addTextChangedListener();
            mTextCpfExaminador1_addTextChangedListener();
            mTextCpfExaminador2_addTextChangedListener();

            btnImportar_setOnClickListener();

            String[] locais = listarTodosOsLocaisDeProvas();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, locais);
            AutoCompleteTextView textView = this.findViewById(R.id.autoCompleteLocalDeProva);
            textView.setAdapter(adapter);

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

    public String[] listarTodosOsLocaisDeProvas() throws NegocioException {

        List<AGC_LocalDeProva> agcLocalDeProvaList = agcLocalDeProvaService.retornarTodosOrdenadoPorDescricao(this);

        String[] lista = new String[agcLocalDeProvaList.size()];
        for (int i = 0; i < agcLocalDeProvaList.size(); i++){
            lista[i] = agcLocalDeProvaList.get(i).getDescricao();
        }

        return lista;

    }

    private void btnImportar_setOnClickListener(){

        mBotaoImportar = this.findViewById(R.id.btnImportarAgendas);
        mBotaoImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTextDataExameInicial = findViewById(R.id.txtDataExameInicial);
                mTextDataExameFinal = findViewById(R.id.txtDataExameFinal);

                mTextCpfExaminador1 = findViewById(R.id.txtCpfExaminador1);
                mTextCpfExaminador2 = findViewById(R.id.txtCpfExaminador2);
                mExame2Rodas = findViewById(R.id.radExame2Rodas);
                mExame4Rodas = findViewById(R.id.radExame4Rodas);
                mLocalExame= findViewById(R.id.autoCompleteLocalDeProva);

                try {

                   final AGC_Usuario agcUsuario = ParametrosAcessoUtil.getAgcUsuarioLogado();
                    if (agcUsuario != null && agcUsuario.getTipoUsuario().equals("E")) {
                        MensagemErroUtil.mostrar("Somente usuários com perfil Gestor podem utilizar este recurso.", view.getContext());
                    } else {

                        String tipoExame = "";
                        if (mExame2Rodas.isChecked()) {
                            tipoExame = "1";
                        } else if (mExame4Rodas.isChecked()) {
                            tipoExame = "2";
                        }

                        String dataExameInicial = formataData(mTextDataExameInicial.getText().toString());
                        String dataExameFinal = formataData(mTextDataExameFinal.getText().toString());

                        if (dataExameInicial.isEmpty() || dataExameFinal.isEmpty()) {
                            MensagemErroUtil.mostrar("Data Inicial e Final devem ser informadas no formadao dd/mm/yyyy", view.getContext());
                        } else {
                            AGC_LocalDeProva agcLocalDeProva = agcLocalDeProvaService.retornarPorDescricao(mLocalExame.getText().toString(), view.getContext());
                            if (agcLocalDeProva == null){
                                throw new NegocioException("Local não informado ou inválido.");
                            }
                            agcProvasCandidatosSinc.sincronizarAgendas(dataExameInicial, dataExameFinal, mTextCpfExaminador1.getText().toString(), mTextCpfExaminador2.getText().toString(), tipoExame, agcLocalDeProva.getId().toString(), view.getContext());
                        }

                        MensagemOkUtil.mostrar("Agendas Sincronizadas com sucesso.", view.getContext());
                    }

                } catch (Exception ex){
                    ex.printStackTrace();
                    MensagemErroUtil.mostrar(ex.getMessage(), view.getContext());
                }
            }
        });

    }

    private String formataData(String data) {
        if (data == null || data.isEmpty() || data.length() != 10){
            return "";
        }
        return data.substring(6, 10) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
    }

    private void mTextCpfExaminador1_addTextChangedListener(){
        mTextCpfExaminador1 = this.findViewById(R.id.txtCpfExaminador1);
        mTextCpfExaminador1.addTextChangedListener(MaskEditUtil.mask(mTextCpfExaminador1, MaskEditUtil.FORMAT_CPF));
    }

    private void mTextCpfExaminador2_addTextChangedListener(){
        mTextCpfExaminador2 = this.findViewById(R.id.txtCpfExaminador2);
        mTextCpfExaminador2.addTextChangedListener(MaskEditUtil.mask(mTextCpfExaminador2, MaskEditUtil.FORMAT_CPF));
    }

    private void mTextDataExameInicial_addTextChangedListener(){
        mTextDataExameInicial = this.findViewById(R.id.txtDataExameInicial);
        mTextDataExameInicial.addTextChangedListener(MaskEditUtil.mask(mTextDataExameInicial, MaskEditUtil.FORMAT_DATE));
    }

    private void mTextDataExameFinal_addTextChangedListener(){
        mTextDataExameFinal = this.findViewById(R.id.txtDataExameFinal);
        mTextDataExameFinal.addTextChangedListener(MaskEditUtil.mask(mTextDataExameFinal, MaskEditUtil.FORMAT_DATE));
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

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
