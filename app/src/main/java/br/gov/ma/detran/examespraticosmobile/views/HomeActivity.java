package br.gov.ma.detran.examespraticosmobile.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Usuario;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;
import br.gov.ma.detran.examespraticosmobile.util.ParametrosAcessoUtil;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ParametrosAcessoUtil parametrosAcessoUtil;
    private AGC_Provas_CandidatosService agcProvasCandidatosService = new AGC_Provas_CandidatosService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        parametrosAcessoUtil=((ParametrosAcessoUtil)getApplicationContext());

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnSincronizarAgenda_setOnClickListener();
        btnAplicarProva_setOnClickListener();
        btnFecharAgenda_setOnClickListener();
    }

    private void btnSincronizarAgenda_setOnClickListener(){
        //NavigationView navigationView = findViewById(R.id.nav_view);
        //View headerView = navigationView.getHeaderView(0);
        Button bSincronizarAgenda = findViewById(R.id.btnSincronizarAgenda);

        final AGC_Usuario agcUsuario = parametrosAcessoUtil.getAgcUsuarioLogado();
        if (agcUsuario != null) {
            if (agcUsuario.getTipoUsuario().equals("E")){
                bSincronizarAgenda.setEnabled(false);
            }
        }

        bSincronizarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direcionarParaImportar();
            }
        });
    }

    private void btnAplicarProva_setOnClickListener(){

        Button bAplicarProva = findViewById(R.id.btnAplicarProva);
        final Context context = this;

        final AGC_Usuario agcUsuario = parametrosAcessoUtil.getAgcUsuarioLogado();
        if (agcUsuario != null) {
            if (agcUsuario.getTipoUsuario().equals("G")){
                bAplicarProva.setEnabled(false);
            }
        }

        bAplicarProva.setOnClickListener(new View.OnClickListener() {
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

        Button bFecharAgenda = findViewById(R.id.btnFecharProva);

        bFecharAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direcionarParaFecharAgenda();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_importar) {
            direcionarParaImportar();
        } else if (id == R.id.nav_aplicar) {

        } else if (id == R.id.nav_fechar) {

        } else*/
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

    private void direcionarParaAplicarProva(AGC_Prova_Candidato agcProvaCandidatoSelecionado) {
        Intent objIndent = new Intent(getApplicationContext(), AplicarProvaActivity.class);
        objIndent.putExtra("idCandidatoSelecionado", agcProvaCandidatoSelecionado.getId().toString());
        startActivity(objIndent);
        finishAffinity();
    }

}
