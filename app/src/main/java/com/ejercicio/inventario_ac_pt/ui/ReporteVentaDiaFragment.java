package com.ejercicio.inventario_ac_pt.ui;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicio.inventario_ac_pt.BD.DBCompra;
import com.ejercicio.inventario_ac_pt.BD.DBProveedor;
import com.ejercicio.inventario_ac_pt.BD.DBReporteDia;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Producto;
import com.ejercicio.inventario_ac_pt.entidades.Proveedor;
import com.ejercicio.inventario_ac_pt.entidades.Vendedor;
import com.ejercicio.inventario_ac_pt.entidades.Venta;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReporteVentaDiaFragment extends Fragment {

    EditText txtDiaSelect;

    Button btnBudcarDia,btnPdfDia;


    TableLayout tblVentasR;


    String NOMBRE_DIRECTORIO = "ProyectoPDFS";
    String NOMBRE_DOCUMENTO = "ReporteVentasDia.pdf";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_reporte_venta_dia, container, false);

        txtDiaSelect = (EditText) root.findViewById(R.id.txtDiaSelect);

        btnBudcarDia = (Button) root.findViewById(R.id.btnBudcarDia) ;
        btnPdfDia = (Button) root.findViewById(R.id.btnPdfDia) ;

        tblVentasR = (TableLayout) root.findViewById(R.id.tblVentasR);

            txtDiaSelect.setOnClickListener(new View.OnClickListener() {
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
        btnBudcarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               buscarFecha(1);
            }});
        btnPdfDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarPDF();
                Toast.makeText(getActivity(), "SE HA GENERADO CORRECTAMENTE EL ARCHIVO", Toast.LENGTH_LONG).show();
            }});



        return root;
    }
    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", i2, i1+1, i);

            // La ponemos en el editText
            txtDiaSelect.setText(fecha);        }

    };

    private void  buscarFecha(int estatus){
        ArrayList<Venta> listaVentas;
        DBReporteDia dbReporteDia = new DBReporteDia(getActivity());
        listaVentas = new ArrayList<>(dbReporteDia.listaVentas( txtDiaSelect.getText().toString()));


        if(tblVentasR.getChildCount()> 1){
            tblVentasR.removeViews(1,tblVentasR.getChildCount());
        }



        for(Venta venta: listaVentas)
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
                textView.setMaxWidth(450);
                textView.setGravity(Gravity.CENTER);
                if(i == 0){ textView.setText(venta.getId()+""); }
                if(i == 1){ textView.setText(venta.getCliente().getNombre_c()); }
                if(i == 2){ textView.setText(venta.getFecha_ve()); }
                if(i == 3){ textView.setText(venta.getCantidadT_ve()+""); }
                if(i == 4){ textView.setText(""+venta.getSubtotal()); }
                if(i == 5){ textView.setText(""+venta.getIVA()); }
                if(i == 6){ textView.setText(""+venta.getTotal()); }

                tableRow.addView(textView);
            }
            tblVentasR.addView(tableRow);
        }


    }

    private void generarPDF(){
        ArrayList<Venta> listaVentas;
        DBReporteDia dbReporteDia = new DBReporteDia(getActivity());
        listaVentas = new ArrayList<>(dbReporteDia.listaVentas( txtDiaSelect.getText().toString()));

        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS

            documento.open();
            documento.add(new Paragraph("REPORTE DE VENTAS DEL DIA \n\n"));
            documento.add(new Paragraph("Fecha seleccionada: "+txtDiaSelect.getText().toString()+"\n\n"));

            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(7);
            tabla.addCell("Clave");
            tabla.addCell("Cliente");
            tabla.addCell("Fecha");
            tabla.addCell("Cantidad");
            tabla.addCell("Subtotal");
            tabla.addCell("IVA");
            tabla.addCell("Total");

            for(Venta venta: listaVentas){
                tabla.addCell(venta.getId()+"");
                tabla.addCell(venta.getCliente().getNombre_c());
                tabla.addCell(venta.getFecha_ve());
                tabla.addCell(venta.getCantidadT_ve()+"");
                tabla.addCell(venta.getSubtotal()+"");
                tabla.addCell(venta.getIVA()+"");
                tabla.addCell(venta.getTotal()+"");

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