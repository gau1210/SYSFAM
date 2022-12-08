package com.example.sysfam.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){

        if (Build.VERSION.SDK_INT >=23){

            List<String>listaPermissoes = new ArrayList<String>();

            /*Percorre as permisssoes passadas no manifest e verifica
            se já foi liberada.
             */
            for(String permissao : permissoes){
               boolean validaPermissao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED;
               if (!validaPermissao) listaPermissoes.add(permissao);
            }
               //Caso a lista esteja vazia, não pe necessário solicitar a permissão
            if (listaPermissoes.isEmpty())
                return true;
                String[] novasPermissoes = new String[listaPermissoes.size()];
                listaPermissoes.toArray(novasPermissoes);

                //Se não solicita a permissão
                ActivityCompat.requestPermissions(activity,novasPermissoes,requestCode);
            }
        return true;
    }
}
