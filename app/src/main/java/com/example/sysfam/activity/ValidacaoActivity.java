package com.example.sysfam.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sysfam.R;
import com.example.sysfam.helper.Permissao;
import com.example.sysfam.helper.Preferencias;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class ValidacaoActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText codPais;
    private EditText codArea;
    private Button enviar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacao);

        Permissao.validaPermissoes(1,this, permissoesNecessarias);

        nome = (EditText) findViewById(R.id.edit_nome);
        telefone = (EditText) findViewById(R.id.edit_telefone);
        codPais = (EditText) findViewById(R.id.edit_cod_pais);
        codArea = (EditText) findViewById(R.id.edit_cod_area);
        enviar = (Button) findViewById(R.id.bt_validar);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codPais.getText().toString() +
                        codArea.getText().toString() +
                        telefone.getText().toString();

                //Gerar um token

                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "SYSFAM código de confirmação:"+ token;

                //Salvar dados para validação
                Preferencias preferencias = new Preferencias(ValidacaoActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario,telefoneCompleto,token);

                //Envio de SMS via emulador
                telefoneCompleto = "5554";
                boolean enviadoSMS = enviaSMS ("+" + telefoneCompleto, mensagemEnvio);

                if (enviadoSMS){

                    Intent intent = new Intent(ValidacaoActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(ValidacaoActivity.this,"Problema em enviar o SMS, tente novamente.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean enviaSMS(String telefone, String mensagem) {

        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void onRequestPermissionsResult(int requestCod, String[] permissions, int[] grantResult){

        super.onRequestPermissionsResult(requestCod, permissions, grantResult);
        for (int resultado:grantResult){
            if (resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();

            }
        }
    }
    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para usar o app é necessário aceitar as pemissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}