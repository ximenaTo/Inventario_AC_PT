package com.ejercicio.inventario_ac_pt.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;

import android.os.Environment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicio.inventario_ac_pt.BD.DBCompra;
import com.ejercicio.inventario_ac_pt.BD.DBDetalleVenta;
import com.ejercicio.inventario_ac_pt.BD.DBVenta;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Compra;
import com.ejercicio.inventario_ac_pt.entidades.DetalleCompra;
import com.ejercicio.inventario_ac_pt.entidades.DetalleVenta;
import com.ejercicio.inventario_ac_pt.entidades.Venta;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class VentaDetalleFragment extends Fragment {

    TextView txtNoVenta,txtCliente,txtClaveCliente, txtCalleCliente,txtFecha;
    TableLayout tblVentas, tblTotalVenta, tblImportes;
    Button btnBuscar,btnBaja,btnPDF;
    Venta ventaA;
    int idVenta;

    String NOMBRE_DIRECTORIO = "ProyectoPDFS";
    String NOMBRE_DOCUMENTO = "ReporteVenta.pdf";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_venta_detalle, container, false);

        txtNoVenta = (TextView) root.findViewById(R.id.txtNoVenta);
        txtClaveCliente = (TextView) root.findViewById(R.id.txtClaveCli);
        txtCliente = (TextView) root.findViewById(R.id.txtCliente);
        txtCalleCliente = (TextView) root.findViewById(R.id.txtCalleCliente);
        txtFecha = (TextView) root.findViewById(R.id.txtFechaVenta);

        btnBuscar = (Button) root.findViewById(R.id.btnBuscarCompra);
        btnBaja = (Button) root.findViewById(R.id.btnEliminarVenta);
        btnPDF = (Button) root.findViewById(R.id.btnPDFVenta);

        tblVentas =  (TableLayout) root.findViewById(R.id.tblProductosAV);
        tblTotalVenta =(TableLayout) root.findViewById(R.id.tblCantidadTotalAV);
        tblImportes = (TableLayout) root.findViewById(R.id.tblTotalesCV);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra = txtNoVenta.getText().toString();
                buscarVenta(palabra);
            }
        });
        btnBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra = txtNoVenta.getText().toString();
                eliminarVenta(Integer.parseInt(palabra));
            }
        });
        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pdfCompra();
            }
        });

        return root;
    }

    private void buscarVenta(String clave){
        Venta venta =   new Venta();
        DBVenta dbVenta = new DBVenta(getActivity());
        venta= dbVenta.buscarVenta(clave);
        ventaA = new Venta();
        ventaA = venta;
        //System.out.println(compraA.getIdCompra());

        if(venta !=null){
            txtCliente.setText(venta.getCliente().getNombre_c());
            txtClaveCliente.setText(""+venta.getCliente().getId());
            txtCalleCliente.setText(venta.getCliente().getCalle_c());
            txtFecha.setText(venta.getFecha_ve());
            listaProductos(venta);
            idVenta = venta.getId();
        }else{
            Toast.makeText(getActivity(), "No se encontro", Toast.LENGTH_LONG).show();
        }

    }

    public void listaProductos(Venta venta){
        if(tblVentas.getChildCount() >1){
            tblVentas.removeViews(1,tblVentas.getChildCount()-1);
            tblTotalVenta.removeViews(1, tblTotalVenta.getChildCount()-1);
            tblImportes.removeViews(1, tblImportes.getChildCount()-1);
        }

        int cantidad=0;
        for(DetalleVenta dv: venta.getDetalleVentaA())
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
                if(i == 0){ textView.setText(dv.getProducto().getClave_p()); }
                if(i == 1){ textView.setText(dv.getProducto().getNombre_p()); }
                if(i == 2){ textView.setText("Par"); }
                if(i == 3){ textView.setText(dv.getProducto().getLinea_p()); }
                if(i == 4){ textView.setText(dv.getCantidad_ve()+""); cantidad += dv.getCantidad_ve(); }
                if(i == 5){ textView.setText(dv.getPrecio_ve()+""); }
                if(i == 6){ textView.setText(dv.getImporte_ve()+""); }

                tableRow.addView(textView);
            }
            tblVentas.addView(tableRow);
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

        tblTotalVenta.addView(tableRow);



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
            if(i == 0){ textView2.setText(venta.getSubtotal()+""); }
            if(i == 1){ textView2.setText(venta.getIVA()+""); }
            if(i == 2){ textView2.setText(venta.getTotal()+""); }


            tableRow2.addView(textView2);
        }
        tblImportes.addView(tableRow2);

    }
    private void eliminarVenta(int id){
        DBVenta dbVenta = new DBVenta(getActivity());
        boolean eliminado = dbVenta.eliminarVenta(id);
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
            documento.add(new Paragraph("REPORTE DE VENTA \n\n"));
            documento.add(new Paragraph("No. Compra: "+ ventaA.getId()+" \n"));
            documento.add(new Paragraph("Proveedor: "+ ventaA.getCliente().getNombre_c()+" \n"));
            documento.add(new Paragraph("Fecha: "+ ventaA.getFecha_ve()+" \n\n\n"));

            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(6);
            tabla.addCell("Clave");
            tabla.addCell("Nombre");
            tabla.addCell("Linea");
            tabla.addCell("Cantidad");
            tabla.addCell("Costo");
            tabla.addCell("Importe");



            for(DetalleVenta dv: ventaA.getDetalleVentaA()){
                tabla.addCell(dv.getProducto().getClave_p());
                tabla.addCell(dv.getProducto().getNombre_p());
                tabla.addCell(dv.getProducto().getLinea_p());
                tabla.addCell(dv.getCantidad_ve()+"");
                tabla.addCell(dv.getPrecio_ve()+"");
                tabla.addCell(dv.getImporte_ve()+"");

            }
            documento.add(tabla);

            documento.add(new Paragraph("\n\n"));
            documento.add(new Paragraph("Subtotal: "+ ventaA.getSubtotal()+" \n"));
            documento.add(new Paragraph("IVA: "+ ventaA.getIVA()+" \n"));
            documento.add(new Paragraph("Total: "+ ventaA.getTotal()+" \n"));

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