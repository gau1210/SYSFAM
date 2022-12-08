package com.example.sysfam.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sysfam.R;
import com.example.sysfam.model.UBS;

import java.util.List;

public class AdapterActivity extends RecyclerView.Adapter{

    List<UBS> ubs;

    public AdapterActivity(List<UBS> ubs) {
        this.ubs = ubs;
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
        UBS produto = ubs.get(position);
        vhClass.tvNome.setText(produto.getNome());
        vhClass.tvStatus.setText(produto.getStatus());
    }

    @Override
    public int getItemCount() {
        return ubs.size();
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
