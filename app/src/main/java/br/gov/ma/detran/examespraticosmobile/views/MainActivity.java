package br.gov.ma.detran.examespraticosmobile.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Falta;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_LocalDeProva;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_FaltaService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_LocalDeProvaService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_UsuariosService;
import br.gov.ma.detran.examespraticosmobile.sincronizacao.AGC_FaltaSinc;
import br.gov.ma.detran.examespraticosmobile.sincronizacao.AGC_LocalDeProvaSinc;
import br.gov.ma.detran.examespraticosmobile.sincronizacao.AGC_UsuariosSinc;
import br.gov.ma.detran.examespraticosmobile.util.ColorStatusBarUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;

public class MainActivity extends AppCompatActivity {

    private View mProgressView;
    private View mFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorStatusBarUtil.setColorStatusBar(this);

        //mProgressView = this.findViewById(R.id.progressBarImportar);
        //mFormView = this.findViewById(R.id.formContentImportar);

        iniciarAplicacao();

        Button bImportar = (Button) findViewById(R.id.btnImportar);
        bImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarAplicacao();
            }
        });

    }

    private void iniciarAplicacao(){

        try {
            AGC_Provas_CandidatosService agcProvasCandidatosService = new AGC_Provas_CandidatosService();
            //agcProvasCandidatosService.limparTabela(this) ;
            //List<AGC_Prova_Candidato> agcProvaCandidatoList = agcProvasCandidatosService.listarTodos(this);

            if (verificarSeTabelaDeUsuariosEstaVazia() == true) {
                importarDadosDosUsuarios();
            }

            if (verificarSeTabelaDeLocaisEstaVazia() == true){
                importarDadosDosLocaisDeProva();
            }

            if (verificarSeTabelaDeFaltasEstaVazia() == true){
                importarDadosDasFaltas();
            }
            direcionarParaLogin();

        } catch (Exception e) {
            e.printStackTrace();
            MensagemErroUtil.mostrar("Erro ao atualizar a base de dados do Tablet. " +
                    "Reinicie a aplicação ou tente novamente clicando no botão Atualizar.", this);
        }
    }

    private Boolean verificarSeTabelaDeUsuariosEstaVazia() throws NegocioException {
        AGC_UsuariosService agcUsuariosService = new AGC_UsuariosService();
        List<AGC_Usuario> agcUsuarios = agcUsuariosService.retornarTodos(this);

        if (agcUsuarios.size() == 0){
            return true;
        } else {
            return false;
        }
    }

    private Boolean verificarSeTabelaDeLocaisEstaVazia() throws NegocioException {
        AGC_LocalDeProvaService agcLocalDeProvaService = new AGC_LocalDeProvaService();
        List<AGC_LocalDeProva> agcLocalDeProvaList = agcLocalDeProvaService.retornarTodosOrdenadoPorId(this);

        if (agcLocalDeProvaList == null || agcLocalDeProvaList.size() == 0){
            return true;
        } else {
            return false;
        }
    }

    private Boolean verificarSeTabelaDeFaltasEstaVazia() throws NegocioException {
        AGC_FaltaService agcFaltaService = new AGC_FaltaService();
        List<AGC_Falta> agcFaltaList = agcFaltaService.listarTodos(this);

        if (agcFaltaList == null || agcFaltaList.size() == 0){
            return true;
        } else {
            return false;
        }
    }

    private void importarDadosDosUsuarios() throws JSONException, NegocioException, IOException {
        AGC_UsuariosSinc agcUsuariosSinc = new AGC_UsuariosSinc();
        agcUsuariosSinc.sincronizarTabelaDeUsuarios(this);
    }

    private void importarDadosDosLocaisDeProva() throws JSONException, NegocioException, IOException {
        AGC_LocalDeProvaSinc agcLocalDeProvaSinc = new AGC_LocalDeProvaSinc();
        agcLocalDeProvaSinc.sincronizarTabelaDeLocaisDeProva(this);
    }

    private void importarDadosDasFaltas() throws JSONException, NegocioException, IOException {
        AGC_FaltaSinc agcFaltaSinc = new AGC_FaltaSinc();
        agcFaltaSinc.sincronizarTabelaDeFaltas(this);
    }

    private void direcionarParaLogin(){
        Intent objIndent = new Intent(getApplicationContext(), LoginActivity.class);
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
