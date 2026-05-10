package com.maxcode.plutus.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxcode.plutus.R;
import com.maxcode.plutus.model.Gasto;

import java.util.List;

public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.ViewHolder> {

    public interface OnEliminarListener {
        void onEliminar(int id);
    }

    private List<Gasto> lista;
    private OnEliminarListener listener;

    public GastosAdapter(List<Gasto> lista, OnEliminarListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gasto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gasto gasto = lista.get(position);
        holder.tvNombre.setText(gasto.getNombre());
        holder.tvMonto.setText(String.format("$%,.0f", gasto.getMonto()));
        holder.tvCategoria.setText(gasto.getCategoria());
        holder.tvFecha.setText(gasto.getFecha());
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(gasto.getId()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMonto, tvCategoria, tvFecha;
        ImageButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_gasto);
            tvMonto = itemView.findViewById(R.id.tv_monto_gasto);
            tvCategoria = itemView.findViewById(R.id.tv_categoria_gasto);
            tvFecha = itemView.findViewById(R.id.tv_fecha_gasto);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_gasto);
        }
    }
}