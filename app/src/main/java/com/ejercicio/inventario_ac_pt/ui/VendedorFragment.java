package com.ejercicio.inventario_ac_pt.ui;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicio.inventario_ac_pt.BD.DBVendedor;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Vendedor;

import java.util.ArrayList;

public class VendedorFragment extends Fragment {

    EditText txtClaveV,txtNombreV,txtCalle,txtColonia,txtTelefono, txtEmail, txtComision;
    Button btnAlta, btnBaja, btnModif, btnBuscar;
    TableLayout tblVendedores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vendedor, container, false);

        txtClaveV = (EditText) root.findViewById(R.id.txtClaveVe);
        txtNombreV = (EditText) root.findViewById(R.id.txtNombreVe);
        txtCalle = (EditText) root.findViewById(R.id.txtCalleVe);
        txtColonia = (EditText) root.findViewById(R.id.txtColoniaVe);
        txtTelefono = (EditText) root.findViewById(R.id.txtTelefonoVe);
        txtEmail = (EditText) root.findViewById(R.id.txtEmailVe);
        txtComision = (EditText) root.findViewById(R.id.txtComisionVe);

        btnAlta = (Button) root.findViewById(R.id.btnAltaVe);
        btnBuscar = (Button) root.findViewById(R.id.btnBiscarVe);

        tblVendedores = (TableLayout) root.findViewById(R.id.tblVendedores);

        listaVendedores();

        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }});



        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra = txtClaveV.getText().toString();
                buscarVendedor(palabra);
            }
        });




        return root;
    }

    private void insertar(){
        DBVendedor dbVendedor = new DBVendedor(getActivity());
        long id= dbVendedor.insertarVendedor(
                txtClaveV.getText().toString(),
                txtNombreV.getText().toString(),
                txtCalle.getText().toString(),
                txtColonia.getText().toString(),
                txtTelefono.getText().toString(),
                txtEmail.getText().toString(),
                Float.parseFloat(txtComision.getText().toString())
        );
        if(id >0){
            Toast.makeText(getActivity(), "Registro guardado", Toast.LENGTH_LONG).show();
            listaVendedores();
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al guardar el registro", Toast.LENGTH_LONG).show();
        }
    }
    public void listaVendedores(){
        ArrayList<Vendedor> listaVendedor;
        DBVendedor dbVendedor = new DBVendedor(getActivity());
        listaVendedor = new ArrayList<>(dbVendedor.listaVendedores());

        for(Vendedor vendedor: listaVendedor)
        {
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < 7; i++) {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setTextColor(Color.BLACK);
                textView.setMinWidth(430);
                textView.setMaxWidth(435);
                textView.setGravity(Gravity.CENTER);
                if(i == 0){ textView.setText(vendedor.getClave()); }
                if(i == 1){ textView.setText(vendedor.getNombre()); }
                if(i == 2){ textView.setText(vendedor.getCalle()); }
                if(i == 3){ textView.setText(vendedor.getColonio()); }
                if(i == 4){ textView.setText(vendedor.getTelefono()); }
                if(i == 5){ textView.setText(vendedor.getEmail()); }
                if(i == 6){ textView.setText(vendedor.getComision()+""); }

                tableRow.addView(textView);
            }
            tblVendedores.addView(tableRow);
        }
    }

    private  void buscarVendedor(String palabra){
        Vendedor ven =   new Vendedor();
        DBVendedor dbVendedor = new DBVendedor(getActivity());
        ven= dbVendedor.buscarVendedor(palabra);
        if(ven !=null){
            txtNombreV.setText(ven.getNombre());
            txtCalle.setText(ven.getCalle());
            txtColonia.setText(ven.getColonio());
            txtTelefono.setText(ven.getTelefono());
            txtEmail.setText(ven.getEmail());
            txtComision.setText(ven.getComision()+"");
        }else{
            Toast.makeText(getActivity(), "No se encontro", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiar(){
        txtClaveV.setText("");
        txtNombreV.setText("");
        txtCalle.setText("");
        txtColonia.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtComision.setText("");
    }



}