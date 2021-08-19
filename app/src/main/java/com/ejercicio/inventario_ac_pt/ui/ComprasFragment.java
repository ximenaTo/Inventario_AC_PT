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

import com.ejercicio.inventario_ac_pt.BD.DBCompra;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Compra;
import com.ejercicio.inventario_ac_pt.entidades.DetalleCompra;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ComprasFragment extends Fragment {
    EditText txtIDCompra;

    TextView txtProveedorD, txtClaveProvD, txtCalleCompraD, txtFechaCompraD;

    Button btnBuscar, btnEliminar, btnPDFCompra;
    TableLayout tblProductosAC, tblCantidadTotalAC, tblTotalesCA;

    ArrayList<DetalleCompra> listDetalle;
    Compra compraA;
    int idCompra;

    String NOMBRE_DIRECTORIO = "ProyectoPDFS";
    String NOMBRE_DOCUMENTO = "ReporteCompra.pdf";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_compras, container, false);
        txtIDCompra = (EditText) root.findViewById(R.id.txtBNoCompra);
        txtProveedorD = (TextView) root.findViewById(R.id.txtProveedorD);
        txtClaveProvD = (TextView) root.findViewById(R.id.txtClaveProvD);
        txtCalleCompraD = (TextView) root.findViewById(R.id.txtCalleCompraD);
        txtFechaCompraD = (TextView) root.findViewById(R.id.txtFechaCompraD);

        btnBuscar  = (Button) root.findViewById(R.id.btnBuscarCompra);
        btnEliminar = (Button) root.findViewById(R.id.btnEliminarCompra);
        btnPDFCompra =  (Button) root.findViewById(R.id.btnPDFCompra);

        tblProductosAC =  (TableLayout) root.findViewById(R.id.tblProductos);
        tblCantidadTotalAC =(TableLayout) root.findViewById(R.id.tblCantidadTotalAC);
        tblTotalesCA = (TableLayout) root.findViewById(R.id.tblTotalesCA);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra = txtIDCompra.getText().toString();
                buscarCompra(palabra);
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarCompra(idCompra);
            }
        });
        btnPDFCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfCompra();
            }
        });


        return root;


    }
    private void buscarCompra(String clave){
        Compra compra =   new Compra();
        DBCompra dbCompra = new DBCompra(getActivity());
        compra= dbCompra.buscarCompra(clave);
        compraA = new Compra();
        compraA = compra;
        //System.out.println(compraA.getIdCompra());

        if(compra !=null){
            txtProveedorD.setText(compra.getProveedor().getNombre_pr());
            txtFechaCompraD.setText(compra.getFecha_c());
            txtClaveProvD.setText(compra.getProveedor().getClave_pr());
            txtCalleCompraD.setText(compra.getProveedor().getCalle_pr());
            listaProductos(compra);
            idCompra = compra.getIdCompra();
        }else{
            Toast.makeText(getActivity(), "No se encontro", Toast.LENGTH_LONG).show();
        }

    }

    public void listaProductos(Compra compra){
        if(tblProductosAC.getChildCount() >1){
            tblProductosAC.removeViews(1,tblProductosAC.getChildCount()-1);
            tblCantidadTotalAC.removeViews(1, tblCantidadTotalAC.getChildCount()-1);
            tblTotalesCA.removeViews(1, tblTotalesCA.getChildCount()-1);
        }



        int cantidad=0;
        for(DetalleCompra dc: compra.getDetalleComprasA())
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
                if(i == 0){ textView.setText(dc.getProducto().getClave_p()); }
                if(i == 1){ textView.setText(dc.getProducto().getNombre_p()); }
                if(i == 2){ textView.setText("Par"); }
                if(i == 3){ textView.setText(dc.getProducto().getLinea_p()); }
                if(i == 4){ textView.setText(dc.getCantidad_c()+""); cantidad += dc.getCantidad_c(); }
                if(i == 5){ textView.setText(dc.getPrecio_ve()+""); }
                if(i == 6){ textView.setText(dc.getImporte_c()+""); }

                tableRow.addView(textView);
            }
            tblProductosAC.addView(tableRow);
        }

        TableRow tableRow = new TableRow(getActivity());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView textView = new TextView(getActivity());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTextColor(Color.BLACK);
        textView.setMinWidth(500);
        textView.setMaxWidth(550);
        textView.setGravity(Gravity.CENTER);
        textView.setText(cantidad+"");

        tableRow.addView(textView);

        tblCantidadTotalAC.addView(tableRow);



        TableRow tableRow2 = new TableRow(getActivity());
        tableRow2.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < 3; i++) {
            TextView textView2 = new TextView(getActivity());
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView2.setTextColor(Color.BLACK);
            textView2.setMinWidth(410);
            textView2.setMaxWidth(420);
            textView2.setGravity(Gravity.CENTER);
            if(i == 0){ textView2.setText(compra.getSubtotal()+""); }
            if(i == 1){ textView2.setText(compra.getIVA()+""); }
            if(i == 2){ textView2.setText(compra.getTotal()+""); }


            tableRow2.addView(textView2);
        }
        tblTotalesCA.addView(tableRow2);

    }

    private void eliminarCompra(int id){
        DBCompra dbCompra = new DBCompra(getActivity());
        boolean eliminado = dbCompra.eliminarCompra(id);
        if(eliminado == true){
            Toast.makeText(getActivity(), "Registro eliminado", Toast.LENGTH_LONG).show();
            //listaProveedores(0);
           // limpiar();
        }else {
            Toast.makeText(getActivity(), "Error eliminado el registro", Toast.LENGTH_LONG).show();
        }

    }
    private void pdfCompra(){

        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS

            documento.open();
            documento.add(new Paragraph("REPORTE DE COMPRA \n\n"));
            documento.add(new Paragraph("No. Compra: "+ compraA.getIdCompra()+" \n"));
            documento.add(new Paragraph("Proveedor: "+ compraA.getProveedor().getNombre_pr()+" \n"));
            documento.add(new Paragraph("Fecha: "+ compraA.getFecha_c()+" \n\n\n"));

            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(6);
            tabla.addCell("Clave");
            tabla.addCell("Nombre");
            tabla.addCell("Linea");
            tabla.addCell("Cantidad");
            tabla.addCell("Costo");
            tabla.addCell("Importe");



            for(DetalleCompra dc: compraA.getDetalleComprasA()){
                tabla.addCell(dc.getProducto().getClave_p());
                tabla.addCell(dc.getProducto().getNombre_p());
                tabla.addCell(dc.getProducto().getLinea_p());
                tabla.addCell(dc.getCantidad_c()+"");
                tabla.addCell(dc.getPrecio_ve()+"");
                tabla.addCell(dc.getImporte_c()+"");

            }
            documento.add(tabla);

            documento.add(new Paragraph("\n\n"));
            documento.add(new Paragraph("Subtotal: "+ compraA.getSubtotal()+" \n"));
            documento.add(new Paragraph("IVA: "+ compraA.getIVA()+" \n"));
            documento.add(new Paragraph("Total: "+ compraA.getTotal()+" \n"));

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