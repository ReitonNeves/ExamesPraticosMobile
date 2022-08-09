package br.gov.ma.detran.examespraticosmobile.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_UsuariosService;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.ParametrosAcessoUtil;
import br.gov.ma.detran.examespraticosmobile.util.SenhaUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText mLoginView;
    private EditText mPasswordView;

    private View mProgressView;
    private View mLoginFormView;

    private ParametrosAcessoUtil parametrosAcessoUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressView = findViewById(R.id.progressBarLogin);
        mLoginFormView = findViewById(R.id.formLogin);

        mLoginView = findViewById(R.id.txtLogin);
        mPasswordView = findViewById(R.id.txtSenha);

        Button mLoginButton = (Button) findViewById(R.id.btnLogin);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar();
            }
        });

        copyFile();

    }

    public void copyFile() {
        try
        {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            File doc = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");


            //if (sd.canWrite())
            //{
                String currentDBPath = "/data/br.gov.ma.detran.examespraticosmobile/databases/AGC_ExamesPraticosDb.db";
                String backupDBPath = "AGC_ExamesPraticosDb2.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(doc, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(this, "Backup Complete", Toast.LENGTH_SHORT).show();
                }
            //}
        }
        catch (Exception e) {
            Log.w("Settings Backup", e);
        }
    }

    private void entrar(){

        try {

            String login = mLoginView.getText().toString();
            String senha = mPasswordView.getText().toString();
            String senhaMD5 = SenhaUtil.gerarMD5(senha);

            AGC_UsuariosService agcUsuariosService = new AGC_UsuariosService();
            AGC_Usuario agcUsuario = agcUsuariosService.retornarPorLoginSenha(login, senhaMD5, this);

            if (agcUsuario == null){
                MensagemErroUtil.mostrar("Usuário ou senha inválida.", this);
            } else {
                parametrosAcessoUtil=((ParametrosAcessoUtil)getApplicationContext());
                parametrosAcessoUtil.setAgcUsuarioLogado(agcUsuario);
                direcionaParaHome();
            }

        } catch (NegocioException ex){
            ex.printStackTrace();
            MensagemErroUtil.mostrar(ex.getMessage(), this);
        } catch (Exception ex){
            ex.printStackTrace();
            MensagemErroUtil.mostrar("Erro: Tarefa não pode ser executada.", this);
        }

    }

    private void direcionaParaHome(){
        Intent objIndent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(objIndent);
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
