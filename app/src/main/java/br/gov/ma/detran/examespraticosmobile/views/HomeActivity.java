package br.gov.ma.detran.examespraticosmobile.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.navigation.NavigationView;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Prova_FaltasService;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.util.ColorStatusBarUtil;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.NegocioException;
import br.gov.ma.detran.examespraticosmobile.util.ParametrosAcessoUtil;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ParametrosAcessoUtil parametrosAcessoUtil;
    private AGC_Provas_CandidatosService agcProvasCandidatosService = new AGC_Provas_CandidatosService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ColorStatusBarUtil.setColorStatusBar(this);
        setSupportActionBar(toolbar);

        parametrosAcessoUtil=((ParametrosAcessoUtil)getApplicationContext());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnSincronizarAgenda_setOnClickListener();
        btnAplicarProva_setOnClickListener();
        btnFecharAgenda_setOnClickListener();
        btnRegistroDePresenca_setOnClickListener();
    }

    private void btnSincronizarAgenda_setOnClickListener(){
        CardView card = findViewById(R.id.card_carregar_exames);

        final AGC_Usuario agcUsuario = parametrosAcessoUtil.getAgcUsuarioLogado();

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(true){//if (agcProvasCandidatosService.verificarSeExistemAgendasPendentesDeEnvioNoTablet(view.getContext()) == false) {
                        direcionarParaImportar();
                    } else{
                        MensagemErroUtil.mostrar("Não é possível sincronizar agendas. Existem registros pendentes de resultados no Tablet. Clique em Fechar Agendas.", view.getContext());
                    }
                } catch (Exception e) {
                    MensagemErroUtil.mostrar(e.getMessage(), view.getContext());
                }
            }
        });
    }

    private void btnAplicarProva_setOnClickListener(){
        final Context context = this;
        CardView card = findViewById(R.id.card_aplicar_exame);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AGC_Prova_Candidato provaIniciada = agcProvasCandidatosService.retornarProvaIniciada(context);
                    if (provaIniciada != null){
                        direcionarParaAplicarProva(provaIniciada);
                    } else {
                        direcionarParaSelecionarCandidato();
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                    MensagemErroUtil.mostrar(ex.getMessage(), context);
                }
            }
        });
    }

    private void btnFecharAgenda_setOnClickListener(){
        CardView card = findViewById(R.id.card_enviar_resultados);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direcionarParaFecharAgenda();
            }
        });
    }

    private void btnRegistroDePresenca_setOnClickListener() {
        //Button bRegistroDePresenca = findViewById(R.id.btnRegistroDePresenca);
        //bRegistroDePresenca.setEnabled(false);
        final Context context = this;
        CardView card = findViewById(R.id.card_registrar_presenca_candidatos);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent objIndent = new Intent(getApplicationContext(), AplicarExameActivity.class);
                startActivity(objIndent);
                finishAffinity();
                direcionarParaFecharAgenda();*/
            }
        });

        /*bRegistroDePresenca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                //AGC_Prova_Candidato provaIniciada = agcProvasCandidatosService.retornarProvaIniciada(context);
                // if (provaIniciada != null){
                    direcionarParaRegistroDePresenca();
                    } else {
                        direcionarParaSelecionarCandidato();
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                    MensagemErroUtil.mostrar(ex.getMessage(), context);
                }
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_limpar_tabelas) {
            try {
                new AGC_Provas_CandidatosService().limparTabela(this);
                new AGC_Prova_FaltasService().limparTabela(this);
            } catch (NegocioException e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sair) {
            parametrosAcessoUtil.setAgcUsuarioLogado(null);
            direcionarParaLogin();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        final AGC_Usuario agcUsuario = parametrosAcessoUtil.getAgcUsuarioLogado();
        if (agcUsuario == null) {
            direcionarParaLogin();
        }else {
            NavigationView navigationView = findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            TextView mUsuarioLogado = headerView.findViewById(R.id.txtUsuarioLogado);
            TextView mTipoUsuarioLogado = headerView.findViewById(R.id.txtTipoUsuarioLogado);

            mUsuarioLogado.setText(agcUsuario.getNome());
            if (agcUsuario.getTipoUsuario().equals("G")){
                mTipoUsuarioLogado.setText("Gestor");
            } else if (agcUsuario.getTipoUsuario().equals("E")){
                mTipoUsuarioLogado.setText("Examinador");
            }
        }

    }

    private void direcionarParaLogin(){
        Intent objIndent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(objIndent);
        finishAffinity();
    }

    private void direcionarParaFecharAgenda(){
        Intent objIndent = new Intent(getApplicationContext(), FecharAgendaActivity.class);
        startActivity(objIndent);
        finishAffinity();
    }

    private void direcionarParaImportar(){
        Intent objIndent = new Intent(getApplicationContext(), ImportarActivity.class);
        startActivity(objIndent);
        finishAffinity();
    }

    private void direcionarParaSelecionarCandidato(){
        Intent objIndent = new Intent(getApplicationContext(), SelecionarCandidatoActivity.class);
        startActivity(objIndent);
        finishAffinity();
    }

    private void direcionarParaRegistroDePresenca(){
        Intent objIndent = new Intent(getApplicationContext(), SelecionarCandidatoActivity.class);
        objIndent.putExtra("registrarPresenca", true);
        startActivity(objIndent);
        finishAffinity();
    }

    private void direcionarParaAplicarProva(AGC_Prova_Candidato agcProvaCandidatoSelecionado) {
        Intent objIndent = new Intent(getApplicationContext(), AplicarProvaActivity.class);
        objIndent.putExtra("idCandidatoSelecionado", agcProvaCandidatoSelecionado.getId().toString());
        startActivity(objIndent);
        finishAffinity();
    }

}
