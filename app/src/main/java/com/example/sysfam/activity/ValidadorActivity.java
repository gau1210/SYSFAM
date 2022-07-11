package com.example.sysfam.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sysfam.R;
import com.example.sysfam.helper.Preferencias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button validar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidacao = (EditText) findViewById(R.id.edit_cod_validacao);
        validar = (Button) findViewById(R.id.bt_validador);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperar as preferências do usuário
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = codigoValidacao.getText().toString();

                if(tokenDigitado.equals(tokenGerado)){

                    Toast.makeText(ValidadorActivity.this, "Token Validado!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ValidadorActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {

                    Toast.makeText(ValidadorActivity.this, "Token Não Validado!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ValidadorActivity.this, ValidacaoActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}