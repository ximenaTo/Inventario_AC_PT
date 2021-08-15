package com.ejercicio.inventario_ac_pt.ui;

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

        btnAlta = (Button) root.findViewById(R.id.btnAltaV);
        btnBaja = (Button) root.findViewById(R.id.btnBajaV);
        btnBuscar = (Button) root.findViewById(R.id.btnBiscarV);
        btnModif = (Button) root.findViewById(R.id.btnModiV);


        tblVendedores = (TableLayout) root.findViewById(R.id.tblVendedores);

        listaVendedores(1);

        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { insertar();}});

        btnBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {eliminar(); }});
        btnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { modificar(); }});


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
            listaVendedores(2);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al guardar el registro", Toast.LENGTH_LONG).show();
        }
    }
    private void eliminar(){
        DBVendedor dbVendedor = new DBVendedor(getActivity());
        boolean eliminado = dbVendedor.eliminarVendedor(txtClaveV.getText().toString());
        if(eliminado == true){
            Toast.makeText(getActivity(), "Registro eliminado", Toast.LENGTH_LONG).show();
            listaVendedores(0);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error eliminado el registro", Toast.LENGTH_LONG).show();
        }
    }

    private  void modificar(){
        DBVendedor dbVendedor = new DBVendedor(getActivity());

        boolean insertado= dbVendedor.modificarVendedor(
                txtClaveV.getText().toString(),
                txtNombreV.getText().toString(),
                txtCalle.getText().toString(),
                txtColonia.getText().toString(),
                txtTelefono.getText().toString(),
                txtEmail.getText().toString(),
                Float.parseFloat(txtComision.getText().toString())
        );
        if(insertado == true){
            Toast.makeText(getActivity(), "Registro modificado", Toast.LENGTH_LONG).show();
            listaVendedores(3);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al modificar el registro", Toast.LENGTH_LONG).show();
        }
    }


    public void listaVendedores(int estatus){
        ArrayList<Vendedor> listaVendedor;
        DBVendedor dbVendedor = new DBVendedor(getActivity());
        listaVendedor = new ArrayList<>(dbVendedor.listaVendedores());

        if(estatus == 2){
           tblVendedores.removeViews(1,listaVendedor.size()-1);
        }
        if(estatus == 0){
            tblVendedores.removeViews(1,listaVendedor.size()+1);
        }
        if(estatus == 3){
            tblVendedores.removeViews(1,listaVendedor.size());
        }

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

    private void refrescarTabla(int size){

    }



}