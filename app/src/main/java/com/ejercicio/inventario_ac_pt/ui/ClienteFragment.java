package com.ejercicio.inventario_ac_pt.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
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

import com.ejercicio.inventario_ac_pt.BD.DBCliente;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Cliente;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClienteFragment extends Fragment {

    EditText txtClaveC,txtNombreC,txtCalleC,txtColoniaC,txtCiudadC,txtrfcC, txtTelefonoC, txtEmailC, txtSaldoC;
    Button btnAltaC, btnBajaC, btnModifC, btnBuscarC, btnPDF;
    TableLayout tblClientes;

    String NOMBRE_DIRECTORIO = "ProyectoPDFS";
    String NOMBRE_DOCUMENTO = "ReporteClientes.pdf";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cliente, container, false);
        txtClaveC = (EditText) root.findViewById(R.id.txtClaveC);
        txtNombreC = (EditText) root.findViewById(R.id.txtNombreC);
        txtCalleC = (EditText) root.findViewById(R.id.txtCalleC);
        txtColoniaC = (EditText) root.findViewById(R.id.txtColoniaC);
        txtCiudadC = (EditText) root.findViewById(R.id.txtCiudadC);
        txtrfcC = (EditText) root.findViewById(R.id.txtRfcC);
        txtTelefonoC = (EditText) root.findViewById(R.id.txtTelefonoC);
        txtEmailC = (EditText) root.findViewById(R.id.txtEmailC);
        txtSaldoC = (EditText) root.findViewById(R.id.txtSueldoC);

        btnAltaC = (Button) root.findViewById(R.id.btnAltaC);
        btnBajaC = (Button) root.findViewById(R.id.btnBajaC);
        btnModifC = (Button) root.findViewById(R.id.btnModC);
        btnBuscarC = (Button) root.findViewById(R.id.btnBuscarC);
        btnPDF = (Button) root.findViewById(R.id.btnPDFCli);


        tblClientes = (TableLayout) root.findViewById(R.id.tblClientes);

        txtSaldoC.setEnabled(false);

        listaClientes(1);

        btnAltaC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { insertarCliente();}});
        btnBajaC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {eliminar(); }});

        btnModifC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { modificar(); }});

        btnBuscarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra = txtClaveC.getText().toString();
                buscarCliente(palabra);
            }
        });

        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearPDFClientes();
                Toast.makeText(getActivity(), "SE CREO EL PDF", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }


    private void insertarCliente(){
        DBCliente dbCliente = new DBCliente(getActivity());
        long id= dbCliente.insertarCliente(
                txtClaveC.getText().toString(),
                txtNombreC.getText().toString(),
                txtCalleC.getText().toString(),
                txtColoniaC.getText().toString(),
                txtCiudadC.getText().toString(),
                txtrfcC.getText().toString(),
                txtTelefonoC.getText().toString(),
                txtEmailC.getText().toString(),
                Float.parseFloat(txtSaldoC.getText().toString())
        );

        if(id >0){
            Toast.makeText(getActivity(), "Registro guardado", Toast.LENGTH_LONG).show();
            listaClientes(2);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al guardar el registro", Toast.LENGTH_LONG).show();
        }
    }
    private void eliminar(){
        DBCliente dbCliente = new DBCliente(getActivity());
        boolean eliminado = dbCliente.eliminarCliente(txtClaveC.getText().toString());
        if(eliminado == true){
            Toast.makeText(getActivity(), "Registro eliminado", Toast.LENGTH_LONG).show();
            listaClientes(0);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error eliminado el registro", Toast.LENGTH_LONG).show();
        }
    }

    private  void modificar(){
        DBCliente dbCliente = new DBCliente(getActivity());
        boolean insertado= dbCliente.modificarCliente(
                txtClaveC.getText().toString(),
                txtNombreC.getText().toString(),
                txtCalleC.getText().toString(),
                txtColoniaC.getText().toString(),
                txtCiudadC.getText().toString(),
                txtrfcC.getText().toString(),
                txtTelefonoC.getText().toString(),
                txtEmailC.getText().toString(),
                Float.parseFloat(txtSaldoC.getText().toString())
        );
        if(insertado == true){
            Toast.makeText(getActivity(), "Registro modificado", Toast.LENGTH_LONG).show();
            listaClientes(3);
            limpiar();
        }else {
            Toast.makeText(getActivity(), "Error al modificar el registro", Toast.LENGTH_LONG).show();
        }
    }

    public void listaClientes(int estatus){
        ArrayList<Cliente> listaCliente;
        DBCliente dbCliente = new DBCliente(getActivity());
        listaCliente = new ArrayList<>(dbCliente.listaClientes());

        if(estatus == 2){
            tblClientes.removeViews(1,listaCliente.size()-1);
        }
        if(estatus == 0){
            tblClientes.removeViews(1,listaCliente.size()+1);
        }
        if(estatus == 3){
            tblClientes.removeViews(1,listaCliente.size());
        }

        for(Cliente cliente: listaCliente)
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
                if(i == 0){ textView.setText(cliente.getClave_c()); }
                if(i == 1){ textView.setText(cliente.getNombre_c()); }
                if(i == 2){ textView.setText(cliente.getCalle_c()); }
                if(i == 3){ textView.setText(cliente.getColonia_c()); }
                if(i == 4){ textView.setText(cliente.getCiudad_c()); }
                if(i == 5){ textView.setText(cliente.getRFC_c()); }
                if(i == 6){ textView.setText(cliente.getTelefono_c()); }
                if(i == 7){ textView.setText(cliente.getEmail_c()); }
                if(i == 8){ textView.setText(cliente.getSaldo_c()+""); }

                tableRow.addView(textView);
            }
            tblClientes.addView(tableRow);
        }

    }

    private  void buscarCliente(String palabra){
        Cliente cliente =   new Cliente();
        DBCliente dbCliente = new DBCliente(getActivity());
        cliente= dbCliente.buscarCliente(palabra);
        if(cliente !=null){
            txtNombreC.setText(cliente.getNombre_c());
            txtCalleC.setText(cliente.getCalle_c());
            txtColoniaC.setText(cliente.getColonia_c());
            txtCiudadC.setText(cliente.getCiudad_c());
            txtrfcC.setText(cliente.getRFC_c());
            txtTelefonoC.setText(cliente.getTelefono_c());
            txtEmailC.setText(cliente.getEmail_c());
            txtSaldoC.setText(cliente.getSaldo_c()+"");
        }else{
            Toast.makeText(getActivity(), "No se encontro", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiar(){
        txtClaveC.setText("");
        txtNombreC.setText("");
        txtCalleC.setText("");
        txtColoniaC.setText("");
        txtCiudadC.setText("");
        txtrfcC.setText("");
        txtTelefonoC.setText("");
        txtEmailC.setText("");
        txtSaldoC.setText("");
    }

    public void crearPDFClientes() {
        ArrayList<Cliente> listaCliente;
        DBCliente dbCliente = new DBCliente(getActivity());
        listaCliente = new ArrayList<>(dbCliente.listaClientes());
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS

            documento.open();
            documento.add(new Paragraph("REPORTE DE CLIENTES \n\n"));

            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(9);
            tabla.addCell("Clave");
            tabla.addCell("Nombre");
            tabla.addCell("Calle");
            tabla.addCell("Colonia");
            tabla.addCell("Ciudad");
            tabla.addCell("RFC");
            tabla.addCell("Telefono");
            tabla.addCell("Email");
            tabla.addCell("Saldo");

            for(Cliente cliente: listaCliente){
                tabla.addCell(cliente.getClave_c());
                tabla.addCell(cliente.getNombre_c());
                tabla.addCell(cliente.getCalle_c());
                tabla.addCell(cliente.getColonia_c());
                tabla.addCell(cliente.getCiudad_c());
                tabla.addCell(cliente.getRFC_c());
                tabla.addCell(cliente.getTelefono_c());
                tabla.addCell(cliente.getEmail_c());
                tabla.addCell(cliente.getSaldo_c()+"");

            }

            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    public File crearFichero(String nombreFichero) {
        File ruta = getRuta();

        File fichero = null;
        if(ruta != null) {
            fichero = new File(ruta, nombreFichero);
        }
        return fichero;
    }


    public File getRuta() {
        File ruta = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), NOMBRE_DIRECTORIO);
            if(ruta != null) {
                if(!ruta.mkdirs()) {
                    if(!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }
}