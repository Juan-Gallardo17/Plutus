package com.maxcode.plutus.ui;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxcode.plutus.R;
import com.maxcode.plutus.model.Meta;

import java.util.List;

public class MetasAdapter extends RecyclerView.Adapter<MetasAdapter.ViewHolder> {

    public interface OnAbonoListener {
        void onAbono(Meta meta, double abono);
    }

    private List<Meta> lista;
    private OnAbonoListener listener;

    public MetasAdapter(List<Meta> lista, OnAbonoListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meta meta = lista.get(position);
        holder.tvNombre.setText(meta.getNombre());
        holder.tvMontos.setText(String.format("$%,.0f de $%,.0f",
                meta.getMontoActual(), meta.getMontoObjetivo()));
        holder.tvPorcentaje.setText(meta.getPorcentaje() + "%");
        holder.progressBar.setProgress(meta.getPorcentaje());

        holder.itemView.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(v.getContext())
                    .inflate(R.layout.dialog_abono_meta, null);
            EditText etAbono = dialogView.findViewById(R.id.et_abono);

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Agregar abono a " + meta.getNombre())
                    .setView(dialogView)
                    .setPositiveButton("Abonar", (dialog, which) -> {
                        String abonoStr = etAbono.getText().toString().trim();
                        if (!abonoStr.isEmpty()) {
                            double abono = Double.parseDouble(abonoStr);
                            listener.onAbono(meta, abono);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMontos, tvPorcentaje;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_meta);
            tvMontos = itemView.findViewById(R.id.tv_montos_meta);
            tvPorcentaje = itemView.findViewById(R.id.tv_porcentaje_meta);
            progressBar = itemView.findViewById(R.id.progress_meta);
        }
    }
}