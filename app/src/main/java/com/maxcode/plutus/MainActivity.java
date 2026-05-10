package com.maxcode.plutus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maxcode.plutus.ui.DeudasFragment;
import com.maxcode.plutus.ui.GastosFragment;
import com.maxcode.plutus.ui.HomeFragment;
import com.maxcode.plutus.ui.MetasFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Cargar HomeFragment por defecto
        cargarFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_gastos) {
                fragment = new GastosFragment();
            } else if (id == R.id.nav_metas) {
                fragment = new MetasFragment();
            } else if (id == R.id.nav_deudas) {
                fragment = new DeudasFragment();
            } else {
                fragment = new HomeFragment();
            }

            cargarFragment(fragment);
            return true;
        });
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}