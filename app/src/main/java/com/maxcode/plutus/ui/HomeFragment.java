package com.maxcode.plutus.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.maxcode.plutus.R;
import com.maxcode.plutus.dao.GastoDAO;
import com.maxcode.plutus.dao.DeudaDAO;

public class HomeFragment extends Fragment {

    private TextView tvTotalGastos, tvTotalDeudas;
    private GastoDAO gastoDAO;
    private DeudaDAO deudaDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gastoDAO = new GastoDAO(requireContext());
        deudaDAO = new DeudaDAO(requireContext());

        tvTotalGastos = view.findViewById(R.id.tv_total_gastos);
        tvTotalDeudas = view.findViewById(R.id.tv_total_deudas);

        cargarDatos();

        return view;
    }

    private void cargarDatos() {
        double totalGastos = gastoDAO.obtenerTotalGastos();
        double totalDeudas = deudaDAO.obtenerTotalPendiente();

        tvTotalGastos.setText(String.format("$%,.0f", totalGastos));
        tvTotalDeudas.setText(String.format("$%,.0f", totalDeudas));
    }
}