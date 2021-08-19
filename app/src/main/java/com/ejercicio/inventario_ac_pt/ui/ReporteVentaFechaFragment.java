package com.ejercicio.inventario_ac_pt.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejercicio.inventario_ac_pt.R;


public class ReporteVentaFechaFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_reporte_venta_fecha, container, false);
        return root;
    }
}