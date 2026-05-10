package com.maxcode.plutus.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maxcode.plutus.R;
import com.maxcode.plutus.dao.DeudaDAO;
import com.maxcode.plutus.model.Deuda;

import java.util.List;

public class DeudasFragment extends Fragment {

    private RecyclerView recyclerView;
    private DeudaDAO deudaDAO;
    private List<Deuda> listaDeudas;
    private DeudasAdapter adapter;
    private TextView tvTotalPendiente;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deudas, container, false);

        deudaDAO = new DeudaDAO(requireContext());

        recyclerView = view.findViewById(R.id.recycler_deudas);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        tvTotalPendiente = view.findViewById(R.id.tv_total_pendiente);

        FloatingActionButton fab = view.findViewById(R.id.fab_agregar_deuda);
        fab.setOnClickListener(v -> mostrarDialogoAgregar());

        cargarDeudas();
        return view;
    }

    private void cargarDeudas() {
        listaDeudas = deudaDAO.obtenerTodos();
        double total = deudaDAO.obtenerTotalPendiente();
        tvTotalPendiente.setText(String.format("Total pendiente: $%,.0f", total));

        adapter = new DeudasAdapter(listaDeudas, (id, nuevoEstado) -> {
            deudaDAO.actualizarEstado(id, nuevoEstado);
            cargarDeudas();
        });
        recyclerView.setAdapter(adapter);
    }

    private void mostrarDialogoAgregar() {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_agregar_deuda, null);

        EditText etNombre = dialogView.findViewById(R.id.et_nombre_deuda);
        EditText etMonto = dialogView.findViewById(R.id.et_monto_deuda);
        EditText etFecha = dialogView.findViewById(R.id.et_fecha_deuda);
        Spinner spinnerEstado = dialogView.findViewById(R.id.spinner_estado);

        String[] estados = {"pendiente", "urgente", "pagado"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, estados);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterSpinner);

        new AlertDialog.Builder(requireContext())
                .setTitle("Agregar deuda")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nombre = etNombre.getText().toString().trim();
                    String montoStr = etMonto.getText().toString().trim();

                    if (nombre.isEmpty() || montoStr.isEmpty()) {
                        Toast.makeText(requireContext(), "Completa los campos obligatorios", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double monto = Double.parseDouble(montoStr);
                    String fecha = etFecha.getText().toString().trim();
                    String estado = spinnerEstado.getSelectedItem().toString();

                    Deuda deuda = new Deuda(nombre, monto, fecha, estado);
                    deudaDAO.insertar(deuda);
                    cargarDeudas();
                    Toast.makeText(requireContext(), "Deuda guardada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}