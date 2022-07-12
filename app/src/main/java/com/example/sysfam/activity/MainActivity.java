package com.example.sysfam.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sysfam.R;
import com.example.sysfam.config.ConfiguracaoFirebase;
import com.example.sysfam.databinding.ActivityNavegacaoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavegacaoBinding binding;
    private FirebaseAuth usuarioAutenticacao;
    private Toolbar toolbar;
    private ImageButton botaoFarmacia;
    private String identificadorRemedio;
    private DatabaseReference firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavegacaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        botaoFarmacia = (ImageButton) findViewById(R.id.bt_farmacia);
        botaoFarmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        usuarioAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_pricipal,R.id.nav_servico,R.id.nav_sobre)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegacao);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegacao);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_configuracoes:
                return true;
            case R.id.item_buscar:
                abrirRemediosDisponiveis();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void abrirRemediosDisponiveis(){

        //Configurando um Dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Buscar");
        alertDialog.setMessage("Especialidade do remedio");
        alertDialog.setCancelable(false);

        EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        //Configurando botões
        alertDialog.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String remedioBuscado = editText.getText().toString();

                //Validar busca
                if(remedioBuscado.isEmpty()){

                    Toast.makeText(MainActivity.this,"Informe a especialidade",Toast.LENGTH_LONG).show();

                }else {
                    //Buscar os dados no Firebase

                    identificadorRemedio = (remedioBuscado);

                    //Rercuperar instancia Firebase
                    firebase = ConfiguracaoFirebase.getFirebase();
                    firebase = firebase.child("especialidade").child(identificadorRemedio);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.getValue()!=null){

                                /*
                                +especialidade
                                    +anti-flamatorio
                                        +endereco
                                            dados
                                    +gripe
                                        +endereco
                                            dados
                                 */

                            }else{

                                Toast.makeText(MainActivity.this,"Não possuimos esse remedio em nenhuma UBS local",Toast.LENGTH_LONG).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        alertDialog.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();

    }
    private void deslogarUsuario(){

        usuarioAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}