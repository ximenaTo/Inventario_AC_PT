package com.ejercicio.inventario_ac_pt.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.ejercicio.inventario_ac_pt.BD.DBProducto;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Producto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class ReporteVentaFechaFragment extends Fragment {

    EditText txtFechaInicio, txtFechaFinal;

    Spinner cmbPVR;
    ArrayList<Producto> listaProducto;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_reporte_venta_fecha, container, false);
        txtFechaInicio = (EditText) root.findViewById(R.id.txtFechaInicio);
        txtFechaFinal = (EditText) root.findViewById(R.id.txtFechaFinal);

        cmbPVR = (Spinner) root.findViewById(R.id.cmbPVR);
        listaProducto();
        txtFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int anio = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogoFecha = new DatePickerDialog(getActivity(), listenerDeDatePicker, anio, mes, diaDelMes);
                //Mostrar
                dialogoFecha.show();

            }});
        txtFechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int anio = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogoFecha = new DatePickerDialog(getActivity(), listenerDeDatePicker2, anio, mes, diaDelMes);
                //Mostrar
                dialogoFecha.show();

            }});
        return root;
    }
    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", i2, i1+1, i);

            // La ponemos en el editText
            txtFechaInicio.setText(fecha);        }

    };
    private DatePickerDialog.OnDateSetListener listenerDeDatePicker2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", i2, i1+1, i);

            // La ponemos en el editText
            txtFechaFinal.setText(fecha);   }

    };

    private void listaProducto(){
        DBProducto dbProducto = new DBProducto(getActivity());
        listaProducto = new ArrayList<>(dbProducto.listaProductos());
        ArrayList<String> arreglo = new ArrayList<>();
        for (Producto producto: listaProducto ) {
            arreglo.add(producto.getClave_p() +" - "+ producto.getNombre_p());
        }
        ArrayAdapter<String> producto = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item , arreglo);
        cmbPVR.setAdapter(producto);
    }

    private void buscarEntreFechas(){

    }





}