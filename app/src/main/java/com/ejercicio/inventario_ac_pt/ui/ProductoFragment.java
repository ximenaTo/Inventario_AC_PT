package com.ejercicio.inventario_ac_pt.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicio.inventario_ac_pt.BD.DBProducto;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Producto;

import java.util.ArrayList;

public class ProductoFragment extends Fragment {
    ArrayList<String> linea=new ArrayList<String>();

    EditText txtClaveP, txtNombrePro, txtExistencia, txtPrecioCosto, txtPrecioVenta1, txtPrecioVenta2, txtPrecioPro;
    Spinner spnLinea;
    Button btnAltaP, btnBajaP, btnModifP, btnBuscarP;
    TableLayout tblProductos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_producto, container, false);
        txtClaveP = (EditText) root.findViewById(R.id.txtClaveP);
        txtNombrePro = (EditText) root.findViewById(R.id.txtNombrePro);
        spnLinea = (Spinner) root.findViewById(R.id.spnLinea);
        txtExistencia = (EditText) root.findViewById(R.id.txtExistencia);
        txtPrecioCosto = (EditText) root.findViewById(R.id.txtPrecioCosto);
        txtPrecioVenta1 = (EditText) root.findViewById(R.id.txtPrecioVenta1);
        txtPrecioVenta2 = (EditText) root.findViewById(R.id.txtPrecioVenta2);
        //txtPrecioPro = (EditText) root.findViewById(R.id.txtPrecioPro);

        btnAltaP = (Button) root.findViewById(R.id.btnAltaP);
        btnBajaP = (Button) root.findViewById(R.id.btnBajaP);
        btnModifP = (Button) root.findViewById(R.id.btnModP);
        btnBuscarP = (Button) root.findViewById(R.id.btnBuscarP);

        tblProductos = (TableLayout) root.findViewById(R.id.tblProductosAC);

        spnLinea.setEnabled(false);
        txtPrecioVenta1.setEnabled(false);
        txtPrecioVenta2.setEnabled(false);
        //txtPrecioPro.setEnabled(false);

        llenarComboLinea();

        listaProductos(1);

        txtPrecioCosto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float precioVenta1 = 0;
                float precioVenta2 = 0;

               try{
                   float precioCosto = Float.parseFloat(txtPrecioCosto.getText().toString());
                   precioVenta1 = precioCosto + ((40 * precioCosto) / 100);
                   precioVenta2 = precioCosto + ((28 * precioCosto) / 100);

               }catch(Exception e){

               }
                txtPrecioVenta1.setText(""+precioVenta1);
                txtPrecioVenta2.setText(""+precioVenta2);
            }
        });

        txtClaveP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String clave = txtClaveP.getText().toString().toUpperCase();
                char [] letra = clave.toCharArray();

                if(letra.length > 0){
                    switch (letra[0]){
                        case 'A':
                            spnLinea.setSelection(1);
                            break;
                        case 'B':
                            spnLinea.setSelection(2);
                            break;
                        case 'C':
                            spnLinea.setSelection(3);
                            break;
                        case 'D':
                            spnLinea.setSelection(4);
                            break;
                        case 'E':
                            spnLinea.setSelection(5);
                            break;
                        case 'R':
                            spnLinea.setSelection(6);
                            break;
                        case 'S':
                            spnLinea.setSelection(7);
                            break;
                        case 'T':
                            spnLinea.setSelection(8);
                            break;
                        case 'U':
                            spnLinea.setSelection(9);
                            break;
                        case 'X':
                            spnLinea.setSelection(10);
                            break;
                    }
                }
            }
        });

        btnAltaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { insertarProducto();}});

        btnBajaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {eliminar(); }});

        btnModifP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { modificar(); }});

        btnBuscarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra = txtClaveP.getText().toString();
                buscarProducto(palabra);
            }
        });

        return root;
    }

    private void insertarProducto(){
        DBProducto dbProducto = new DBProducto(getActivity());

        long id= dbProducto.insertarProducto(
                txtClaveP.getText().toString(),
                txtNombrePro.getText().toString(),
                spnLinea.getItemAtPosition(spnLinea.getSelectedItemPosition()).toString(),
                txtExistencia.getText().toString(),
                Float.parseFloat(txtPrecioCosto.getText().toString()),
                Float.parseFloat(txtPrecioVenta1.getText().toString()),
                Float.parseFloat(txtPrecioVenta2.getText().toString())
                //Float.parseFloat(txtPrecioPro.getText().toString())
        );

        if(id >0){
            Toast.makeText(getActivity(), "Registro guardado", Toast.LENGTH_LONG).show();
            listaProductos(2);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al guardar el registro", Toast.LENGTH_LONG).show();
        }
    }

    private  void modificar(){
        DBProducto dbProducto = new DBProducto(getActivity());
        boolean insertado= dbProducto.modificarProducto(
                txtClaveP.getText().toString(),
                txtNombrePro.getText().toString(),
                spnLinea.getItemAtPosition(spnLinea.getSelectedItemPosition()).toString(),
                txtExistencia.getText().toString(),
                Float.parseFloat(txtPrecioCosto.getText().toString()),
                Float.parseFloat(txtPrecioVenta1.getText().toString()),
                Float.parseFloat(txtPrecioVenta2.getText().toString())
                //Float.parseFloat(txtPrecioPro.getText().toString())
        );
        if(insertado == true){
            Toast.makeText(getActivity(), "Registro modificado", Toast.LENGTH_LONG).show();
            listaProductos(3);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al modificar el registro", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminar(){
        DBProducto dbProducto = new DBProducto(getActivity());
        boolean eliminado = dbProducto.eliminarProducto(txtClaveP.getText().toString());
        if(eliminado == true){
            Toast.makeText(getActivity(), "Registro eliminado", Toast.LENGTH_LONG).show();
            listaProductos(0);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error eliminado el registro", Toast.LENGTH_LONG).show();
        }
    }

    public void listaProductos(int estatus){
        ArrayList<Producto> listaProducto;
        DBProducto dbProducto = new DBProducto(getActivity());
        listaProducto = new ArrayList<>(dbProducto.listaProductos());

        if(estatus == 2){
            tblProductos.removeViews(1,listaProducto.size()-1);
        }
        if(estatus == 0){
            tblProductos.removeViews(1,listaProducto.size()+1);
        }
        if(estatus == 3){
            tblProductos.removeViews(1,listaProducto.size());
        }

        for(Producto producto: listaProducto)
        {
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < 8; i++) {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setTextColor(Color.BLACK);
                textView.setMinWidth(375);
                textView.setMaxWidth(425);
                textView.setGravity(Gravity.CENTER);
                if(i == 0){ textView.setText(producto.getClave_p()); }
                if(i == 1){ textView.setText(producto.getNombre_p()); }
                if(i == 2){ textView.setText(producto.getLinea_p()); }
                if(i == 3){ textView.setText(producto.getExistencia_p()); }
                if(i == 4){ textView.setText(""+producto.getPrecioCosto_p()); }
                if(i == 5){ textView.setText(""+producto.getPrecioVenta1_p()); }
                if(i == 6){ textView.setText(""+producto.getPrecioventa2_p()); }
                if(i == 7){ textView.setText(""+producto.getPrecioPromedio_p()); }

                tableRow.addView(textView);
            }
            tblProductos.addView(tableRow);
        }

    }


    public void llenarComboLinea(){
        linea.add("Seleccione");
        linea.add("Hombre 25 -30");
        linea.add("Joven  22 -25");
        linea.add("Ni??o 18 - 21");
        linea.add("Ni??o 15-17");
        linea.add("Ni??o 12-14");
        linea.add("Dama 22 - 26");
        linea.add("Ni??a 18 -21");
        linea.add("Ni??a 15 - 17");
        linea.add("Ni??a 12 -14");
        linea.add("BEBE 10 -12");

        ArrayAdapter<CharSequence> spnListLinea = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,linea);
        spnLinea.setAdapter(spnListLinea);
    }

    private  void buscarProducto(String palabra){
        Producto producto =   new Producto();
        DBProducto dbProducto = new DBProducto(getActivity());
        producto= dbProducto.buscarProducto(palabra);

        int valorSpnLinea = setItem(linea,producto.getLinea_p());

        if(producto !=null){
            txtNombrePro.setText(producto.getNombre_p());
            spnLinea.setSelection(valorSpnLinea);
            txtExistencia.setText(producto.getExistencia_p());
            txtPrecioCosto.setText(""+producto.getPrecioCosto_p());
            txtPrecioVenta1.setText(""+producto.getPrecioVenta1_p());
            txtPrecioVenta2.setText(""+producto.getPrecioventa2_p());
            //txtPrecioPro.setText(""+producto.getPrecioPromedio_p());
        }else{
            Toast.makeText(getActivity(), "No se encontro", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiar(){
        txtClaveP.setText("");
        txtNombrePro.setText("");
        spnLinea.setSelection(0);
        txtExistencia.setText("");
        txtPrecioCosto.setText("");
        txtPrecioVenta1.setText("");
        txtPrecioVenta2.setText("");
        //txtPrecioPro.setText("");
    }

    public int setItem(ArrayList<String>lista,String valor){
        int cursor = 0;
        for (int i=0; i < lista.size();i++){
            if (lista.get(i).equals(valor)){
                cursor = i;
                break;
            }
        }
        return cursor;
    }

}