package com.maxcode.plutus.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maxcode.plutus.R;
import com.maxcode.plutus.dao.MetaDAO;
import com.maxcode.plutus.model.Meta;

import java.util.List;

public class MetasFragment extends Fragment {

    private RecyclerView recyclerView;
    private MetaDAO metaDAO;
    private List<Meta> listaMetas;
    private MetasAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_metas, container, false);

        metaDAO = new MetaDAO(requireContext());

        recyclerView = view.findViewById(R.id.recycler_metas);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab_agregar_meta);
        fab.setOnClickListener(v -> mostrarDialogoAgregar());

        cargarMetas();
        return view;
    }

    private void cargarMetas() {
        listaMetas = metaDAO.obtenerTodos();
        adapter = new MetasAdapter(listaMetas, (meta, abono) -> {
            meta.setMontoActual(meta.getMontoActual() + abono);
            metaDAO.actualizar(meta);
            cargarMetas();
        });
        recyclerView.setAdapter(adapter);
    }

    private void mostrarDialogoAgregar() {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_agregar_meta, null);

        EditText etNombre = dialogView.findViewById(R.id.et_nombre_meta);
        EditText etObjetivo = dialogView.findViewById(R.id.et_monto_objetivo);
        EditText etFecha = dialogView.findViewById(R.id.et_fecha_meta);

        new AlertDialog.Builder(requireContext())
                .setTitle("Nueva meta de ahorro")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nombre = etNombre.getText().toString().trim();
                    String objetivoStr = etObjetivo.getText().toString().trim();

                    if (nombre.isEmpty() || objetivoStr.isEmpty()) {
                        Toast.makeText(requireContext(), "Completa los campos obligatorios", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double objetivo = Double.parseDouble(objetivoStr);
                    String fecha = etFecha.getText().toString().trim();

                    Meta meta = new Meta(nombre, objetivo, 0, fecha);
                    metaDAO.insertar(meta);
                    cargarMetas();
                    Toast.makeText(requireContext(), "Meta creada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}