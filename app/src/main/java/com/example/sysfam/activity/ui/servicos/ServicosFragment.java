package com.example.sysfam.activity.ui.servicos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sysfam.R;
import com.example.sysfam.config.ConfiguracaoFirebase;
import com.example.sysfam.helper.Preferencias;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServicosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> UBS;
    private DatabaseReference firebase;

    public ServicosFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //instância objetos
        UBS = new ArrayList<>();
        UBS.add("UBS São Marcos");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicos, container, false);

        //Monta Listview e adapter
        listView = (ListView) view.findViewById(R.id.lv_UBS);
        adapter = new ArrayAdapter(
                getActivity(),
                R.layout.lista_ubs,
                UBS
        );
        listView.setAdapter(adapter);

        //Recuperar contatos do firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("UBS");

        return view;
    }
}