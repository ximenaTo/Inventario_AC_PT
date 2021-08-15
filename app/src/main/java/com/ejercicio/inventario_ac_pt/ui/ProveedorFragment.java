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

import com.ejercicio.inventario_ac_pt.BD.DBProveedor;
import com.ejercicio.inventario_ac_pt.BD.DBVendedor;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Proveedor;

import java.util.ArrayList;


public class ProveedorFragment extends Fragment {
    EditText txtClavePr,txtNombrePr,txtCallePr,txtColoniaPr,txtCiudadPr,txtrfcPr, txtTelefonoPr, txtEmailPr, txtSaldoPr;
    Button btnAltaPr, btnBajaPr, btnModifPr, btnBuscarPr;
    TableLayout tblProveedores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_proveedor, container, false);
        txtClavePr = (EditText) root.findViewById(R.id.txtClavePr);
        txtNombrePr = (EditText) root.findViewById(R.id.txtNombrePr);
        txtCallePr = (EditText) root.findViewById(R.id.txtCallePr);
        txtColoniaPr = (EditText) root.findViewById(R.id.txtColoniaPr);
        txtCiudadPr = (EditText) root.findViewById(R.id.txtCiudadPr);
        txtrfcPr = (EditText) root.findViewById(R.id.txtRfcPr);
        txtTelefonoPr = (EditText) root.findViewById(R.id.txtTelefonoPr);
        txtEmailPr = (EditText) root.findViewById(R.id.txtEmailPr);
        txtSaldoPr = (EditText) root.findViewById(R.id.txtSueldoPr);

        btnAltaPr = (Button) root.findViewById(R.id.btnAltaPr);
        btnBajaPr = (Button) root.findViewById(R.id.btnBajaPr);
        btnModifPr = (Button) root.findViewById(R.id.btnModPr);
        btnBuscarPr = (Button) root.findViewById(R.id.btnBuscarPr);


        tblProveedores = (TableLayout) root.findViewById(R.id.tblProveedores);

        listaProveedores(1);

        btnAltaPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { insertarProveedor();}});
        btnBajaPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {eliminar(); }});

        btnModifPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { modificar(); }});

        btnBuscarPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra = txtClavePr.getText().toString();
                buscarProveedor(palabra);
            }
        });

        return root;
    }

    private void insertarProveedor(){
        DBProveedor dbProveedor = new DBProveedor(getActivity());
        long id= dbProveedor.insertarProveedor(
                txtClavePr.getText().toString(),
                txtNombrePr.getText().toString(),
                txtCallePr.getText().toString(),
                txtColoniaPr.getText().toString(),
                txtCiudadPr.getText().toString(),
                txtrfcPr.getText().toString(),
                txtTelefonoPr.getText().toString(),
                txtEmailPr.getText().toString(),
                Float.parseFloat(txtSaldoPr.getText().toString())
        );

        if(id >0){
            Toast.makeText(getActivity(), "Registro guardado", Toast.LENGTH_LONG).show();
            listaProveedores(2);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al guardar el registro", Toast.LENGTH_LONG).show();
        }
    }
    private void eliminar(){
        DBProveedor dbProveedor = new DBProveedor(getActivity());
        boolean eliminado = dbProveedor.eliminarProveedor(txtClavePr.getText().toString());
        if(eliminado == true){
            Toast.makeText(getActivity(), "Registro eliminado", Toast.LENGTH_LONG).show();
            listaProveedores(0);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error eliminado el registro", Toast.LENGTH_LONG).show();
        }
    }

    private  void modificar(){
        DBProveedor dbProveedor = new DBProveedor(getActivity());
        boolean insertado= dbProveedor.modificarProveedor(
                txtClavePr.getText().toString(),
                txtNombrePr.getText().toString(),
                txtCallePr.getText().toString(),
                txtColoniaPr.getText().toString(),
                txtCiudadPr.getText().toString(),
                txtrfcPr.getText().toString(),
                txtTelefonoPr.getText().toString(),
                txtEmailPr.getText().toString(),
                Float.parseFloat(txtSaldoPr.getText().toString())
        );
        if(insertado == true){
            Toast.makeText(getActivity(), "Registro modificado", Toast.LENGTH_LONG).show();
            listaProveedores(3);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al modificar el registro", Toast.LENGTH_LONG).show();
        }
    }

    public void listaProveedores(int estatus){
        ArrayList<Proveedor> listaProveedor;
        DBProveedor dbProveedor = new DBProveedor(getActivity());
        listaProveedor = new ArrayList<>(dbProveedor.listaProveedores());

        if(estatus == 2){
            tblProveedores.removeViews(1,listaProveedor.size()-1);
        }
        if(estatus == 0){
            tblProveedores.removeViews(1,listaProveedor.size()+1);
        }
        if(estatus == 3){
            tblProveedores.removeViews(1,listaProveedor.size());
        }

        for(Proveedor proveedor: listaProveedor)
        {
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < 9; i++) {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setTextColor(Color.BLACK);
                textView.setMinWidth(330);
                textView.setMaxWidth(350);
                textView.setGravity(Gravity.CENTER);
                if(i == 0){ textView.setText(proveedor.getClave_pr()); }
                if(i == 1){ textView.setText(proveedor.getNombre_pr()); }
                if(i == 2){ textView.setText(proveedor.getCalle_pr()); }
                if(i == 3){ textView.setText(proveedor.getColonia_pr()); }
                if(i == 4){ textView.setText(proveedor.getCiudad_pr()); }
                if(i == 5){ textView.setText(proveedor.getRFC_pr()); }
                if(i == 6){ textView.setText(proveedor.getTelefono_pr()); }
                if(i == 7){ textView.setText(proveedor.getEmail_pr()); }
                if(i == 8){ textView.setText(proveedor.getSaldo_pr()+""); }

                tableRow.addView(textView);
            }
            tblProveedores.addView(tableRow);
        }

    }

    private  void buscarProveedor(String palabra){
        Proveedor proveedor =   new Proveedor();
        DBProveedor dbProveedor = new DBProveedor(getActivity());
        proveedor= dbProveedor.buscarProveedor(palabra);
        if(proveedor !=null){
            txtNombrePr.setText(proveedor.getNombre_pr());
            txtCallePr.setText(proveedor.getCalle_pr());
            txtColoniaPr.setText(proveedor.getColonia_pr());
            txtCiudadPr.setText(proveedor.getCiudad_pr());
            txtrfcPr.setText(proveedor.getRFC_pr());
            txtTelefonoPr.setText(proveedor.getTelefono_pr());
            txtEmailPr.setText(proveedor.getEmail_pr());
            txtSaldoPr.setText(proveedor.getSaldo_pr()+"");
        }else{
            Toast.makeText(getActivity(), "No se encontro", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiar(){
        txtClavePr.setText("");
        txtNombrePr.setText("");
        txtCallePr.setText("");
        txtColoniaPr.setText("");
        txtCiudadPr.setText("");
        txtrfcPr.setText("");
        txtTelefonoPr.setText("");
        txtEmailPr.setText("");
        txtSaldoPr.setText("");
    }
}