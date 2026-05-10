package com.maxcode.plutus.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maxcode.plutus.R;
import com.maxcode.plutus.dao.GastoDAO;
import com.maxcode.plutus.model.Gasto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GastosFragment extends Fragment {

    private RecyclerView recyclerView;
    private GastoDAO gastoDAO;
    private List<Gasto> listaGastos;
    private GastosAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gastos, container, false);

        gastoDAO = new GastoDAO(requireContext());

        recyclerView = view.findViewById(R.id.recycler_gastos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab_agregar_gasto);
        fab.setOnClickListener(v -> mostrarDialogoAgregar());

        cargarGastos();
        return view;
    }

    private void cargarGastos() {
        listaGastos = gastoDAO.obtenerTodos();
        adapter = new GastosAdapter(listaGastos, id -> {
            gastoDAO.eliminar(id);
            cargarGastos();
        });
        recyclerView.setAdapter(adapter);
    }

    private void mostrarDialogoAgregar() {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_agregar_gasto, null);

        EditText etNombre = dialogView.findViewById(R.id.et_nombre_gasto);
        EditText etMonto = dialogView.findViewById(R.id.et_monto_gasto);
        Spinner spinnerCategoria = dialogView.findViewById(R.id.spinner_categoria);

        String[] categorias = {"Comida", "Transporte", "Educación", "Ocio", "Salud", "Ropa", "Otro"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, categorias);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterSpinner);

        new AlertDialog.Builder(requireContext())
                .setTitle("Agregar gasto")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nombre = etNombre.getText().toString().trim();
                    String montoStr = etMonto.getText().toString().trim();

                    if (nombre.isEmpty() || montoStr.isEmpty()) {
                        Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double monto = Double.parseDouble(montoStr);
                    String categoria = spinnerCategoria.getSelectedItem().toString();
                    String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    Gasto gasto = new Gasto(nombre, monto, categoria, fecha);
                    gastoDAO.insertar(gasto);
                    cargarGastos();
                    Toast.makeText(requireContext(), "Gasto guardado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}