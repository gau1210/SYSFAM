package com.example.sysfam.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sysfam.R;
import com.example.sysfam.model.Remedios;

import java.util.List;

public class AdapterActivity extends RecyclerView.Adapter{

    List<Remedios> list;

    public AdapterActivity(List<Remedios> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ubs,parent,false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass vhClass = (ViewHolderClass) holder;
        Remedios remedios = list.get(position);
        vhClass.tvNome.setText(remedios.getNome());
        vhClass.tvStatus.setText(remedios.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView tvNome;
        TextView tvStatus;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
