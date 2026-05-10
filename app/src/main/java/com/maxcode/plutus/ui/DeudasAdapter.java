package com.maxcode.plutus.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxcode.plutus.R;
import com.maxcode.plutus.model.Deuda;

import java.util.List;

public class DeudasAdapter extends RecyclerView.Adapter<DeudasAdapter.ViewHolder> {

    public interface OnEstadoListener {
        void onCambiarEstado(int id, String nuevoEstado);
    }

    private List<Deuda> lista;
    private OnEstadoListener listener;

    public DeudasAdapter(List<Deuda> lista, OnEstadoListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deuda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Deuda deuda = lista.get(position);
        holder.tvNombre.setText(deuda.getNombre());
        holder.tvMonto.setText(String.format("$%,.0f", deuda.getMonto()));
        holder.tvFecha.setText(deuda.getFechaLimite() != null ? deuda.getFechaLimite() : "Sin fecha");
        holder.tvEstado.setText(deuda.getEstado());

        switch (deuda.getEstado()) {
            case "pagado":
                holder.tvEstado.setBackgroundResource(R.drawable.bg_estado_pagado);
                holder.tvEstado.setTextColor(0xFF27500A);
                break;
            case "urgente":
                holder.tvEstado.setBackgroundResource(R.drawable.bg_estado_urgente);
                holder.tvEstado.setTextColor(0xFF791F1F);
                break;
            default:
                holder.tvEstado.setBackgroundResource(R.drawable.bg_estado_pendiente);
                holder.tvEstado.setTextColor(0xFF633806);
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            String[] estados = {"pendiente", "urgente", "pagado"};
            new android.app.AlertDialog.Builder(v.getContext())
                    .setTitle("Cambiar estado")
                    .setItems(estados, (dialog, which) ->
                            listener.onCambiarEstado(deuda.getId(), estados[which]))
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMonto, tvFecha, tvEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_deuda);
            tvMonto = itemView.findViewById(R.id.tv_monto_deuda);
            tvFecha = itemView.findViewById(R.id.tv_fecha_deuda);
            tvEstado = itemView.findViewById(R.id.tv_estado_deuda);
        }
    }
}