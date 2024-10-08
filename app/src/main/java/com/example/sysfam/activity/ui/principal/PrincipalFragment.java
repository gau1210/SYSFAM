package com.example.sysfam.activity.ui.principal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.sysfam.R;
import com.example.sysfam.activity.AdapterActivity;
import com.example.sysfam.config.ConfiguracaoFirebase;
import com.example.sysfam.model.Remedios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrincipalFragment extends Fragment {

    ArrayList<Remedios> list;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        searchView = view.findViewById(R.id.seachView);
        recyclerView = view.findViewById(R.id.recyclerView);

        list = new ArrayList<>();
        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("UBS").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dados:snapshot.getChildren()){
                list.add(dados.getValue(Remedios.class));

                }
                AdapterActivity adapterActivity = new AdapterActivity(list);
                recyclerView.setAdapter(adapterActivity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
        return view;
    }
    private void search(String str) {
        ArrayList<Remedios> myList = new ArrayList<>();
        for (Remedios object : list){
            if (object.getNome().toLowerCase().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        AdapterActivity adapterActivity = new AdapterActivity(myList);
        recyclerView.setAdapter(adapterActivity);
    }
}