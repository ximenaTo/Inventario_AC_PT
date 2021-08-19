package com.ejercicio.inventario_ac_pt.ui.gallery;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ejercicio.inventario_ac_pt.BD.DBCompra;
import com.ejercicio.inventario_ac_pt.BD.DBProducto;
import com.ejercicio.inventario_ac_pt.BD.DBProveedor;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Compra;
import com.ejercicio.inventario_ac_pt.entidades.DetalleCompra;
import com.ejercicio.inventario_ac_pt.entidades.PrecioProm;
import com.ejercicio.inventario_ac_pt.entidades.Producto;
import com.ejercicio.inventario_ac_pt.entidades.Proveedor;
import com.ejercicio.inventario_ac_pt.entidades.Vendedor;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    Spinner productosC,proveedorC ;

    TextView txtClaveProv, txtCalleCompra, txtNoCompra, txtFechaCompra ;
    EditText txtCantidadProd;

    CheckBox chbFacturacion;

    Button btnAgregarPC, btnAgregarC;

    TableLayout tblProductosCompra,tblCantTotal, tblTotalesCompra;

    int totalCalzado =0;
    float iva = 0;
    float subtotal =0;
    float total =0;

    int idProveedor;

    ArrayList<Proveedor> listaProveedor;
    ArrayList<Vendedor> listaVendedor;
    ArrayList<Producto> listaProducto;
    ArrayList<DetalleCompra> detalleCompraArrayList = new ArrayList<>();
    ArrayList<PrecioProm> precioProms = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        productosC = (Spinner) root.findViewById(R.id.cmbProductoC);

        proveedorC = (Spinner) root.findViewById(R.id.cmbProveedorC);

        txtClaveProv = (TextView) root.findViewById(R.id.txtClaveProvD);
        txtCalleCompra =  (TextView) root.findViewById(R.id.txtCalleCompraD);
        txtNoCompra =  (TextView) root.findViewById(R.id.txtNoCompra);
        txtFechaCompra = (TextView) root.findViewById(R.id.txtFechaCompraD);

        txtCantidadProd = (EditText) root.findViewById(R.id.txtCantidadProd);

        chbFacturacion = (CheckBox) root.findViewById(R.id.chbFacturacion);

        btnAgregarPC = (Button)  root.findViewById(R.id.btnAgregarPC);
        btnAgregarC = (Button)  root.findViewById(R.id.btnAgregarC);

        tblProductosCompra = (TableLayout) root.findViewById(R.id.tblProductosA);

        tblCantTotal = (TableLayout) root.findViewById(R.id.tblCantidadTotalAC);
        tblTotalesCompra = (TableLayout) root.findViewById(R.id.tblTotalesCA);

        listaProveedor();

        listaProducto();
        numeroCompra();
        fechaCompra();


        btnAgregarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarCompra();
                Toast.makeText(getActivity(), "SE CREO LA COMPRA", Toast.LENGTH_LONG).show();
            }
        });
        chbFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean factura = chbFacturacion.isChecked();
                if (factura != false){
                    iva = subtotal * 0.16f;
                    total = iva + subtotal;
                    listaProductos(2);
                }else {
                    total = subtotal;
                    iva =0;
                    listaProductos(2);
                }

            }
        });
        proveedorC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccion=proveedorC.getSelectedItem().toString();
                String[] parts = seleccion.split( " - ");
                Proveedor prov;
                DBProveedor dbProveedor = new DBProveedor(getActivity());
                prov= dbProveedor.buscarProveedor(parts[0]);
                txtClaveProv.setText(prov.getClave_pr());
                txtCalleCompra.setText(prov.getCalle_pr());
                idProveedor = prov.getId();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAgregarPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seleccion=productosC.getSelectedItem().toString();
                String[] parts = seleccion.split( " - ");

                agregarProducto(parts[0]);
                Toast.makeText(getActivity(), "SE AGREGO EL CALZADO", Toast.LENGTH_LONG).show();
                }});

        return root;
    }
    private void listaProveedor(){
        DBProveedor dbProveedor = new DBProveedor(getActivity());
        listaProveedor = new ArrayList<>(dbProveedor.listaProveedores());
        ArrayList<String> arreglo = new ArrayList<>();
        for (Proveedor proveedor: listaProveedor ) {
            arreglo.add(proveedor.getClave_pr() +" - "+ proveedor.getNombre_pr());
        }
        ArrayAdapter<String> proveedor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item , arreglo);
        proveedorC.setAdapter(proveedor);
    }


    private void listaProducto(){
        DBProducto dbProducto = new DBProducto(getActivity());
        listaProducto = new ArrayList<>(dbProducto.listaProductos());
        ArrayList<String> arreglo = new ArrayList<>();
        for (Producto producto: listaProducto ) {
            arreglo.add(producto.getClave_p() +" - "+ producto.getNombre_p());
        }
        ArrayAdapter<String> producto = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item , arreglo);
        productosC.setAdapter(producto);
    }



    private void numeroCompra(){
        DBCompra dbCotizacion = new DBCompra(getActivity());
        int numeroCompra = dbCotizacion.numeroCompra();
        numeroCompra = numeroCompra+1;
        txtNoCompra.setText(numeroCompra+"");
    }

    private void fechaCompra(){
        Date objDate = new Date();
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String fecha = objSDF.format(objDate);
            txtFechaCompra.setText(fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void agregarProducto(String palabra){
        int cant = Integer.parseInt(txtCantidadProd.getText().toString());
        Producto producto =   new Producto();
        DBProducto dbProducto = new DBProducto(getActivity());
        producto= dbProducto.buscarProducto(palabra);
        DetalleCompra dc = new DetalleCompra();

        dc.setProducto(producto);
        dc.setCantidad_c(cant);
        dc.setPrecio_ve(producto.getPrecioCosto_p());
        dc.setImporte_c(cant*producto.getPrecioCosto_p());
        totalCalzado += cant;
        subtotal+= cant *producto.getPrecioCosto_p();
        total = subtotal;
        detalleCompraArrayList.add(dc);
        float precioP= producto.getPrecioCosto_p()/cant;
        PrecioProm pp = new PrecioProm(producto.getClave_p(),precioP);
        precioProms.add(pp);
        System.out.println(precioProms.get(0).toString());
        int estatus = 0;
        if(detalleCompraArrayList.size()>1)
            estatus = 1;
        listaProductos(estatus);

    }

    public void listaProductos(int estatus){
        if(estatus == 1){
            tblProductosCompra.removeViews(1,detalleCompraArrayList.size()-1);
            tblCantTotal.removeViewAt(1);
            tblTotalesCompra.removeViewAt(1);
        }
        if(estatus == 2){
            tblProductosCompra.removeViews(1,detalleCompraArrayList.size());
            tblCantTotal.removeViewAt(1);
            tblTotalesCompra.removeViewAt(1);
        }

        for(DetalleCompra dc: detalleCompraArrayList)
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
                if(i == 4){ textView.setText(dc.getCantidad_c()+""); }
                if(i == 5){ textView.setText(dc.getPrecio_ve()+""); }
                if(i == 6){ textView.setText(dc.getImporte_c()+""); }

                tableRow.addView(textView);
            }
            tblProductosCompra.addView(tableRow);
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
                textView.setText(totalCalzado+"");

                tableRow.addView(textView);

            tblCantTotal.addView(tableRow);



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
                if(i == 0){ textView2.setText(subtotal+""); }
                if(i == 1){ textView2.setText(iva+""); }
                if(i == 2){ textView2.setText(total+""); }


                tableRow2.addView(textView2);
            }
            tblTotalesCompra.addView(tableRow2);




        }

        private void agregarCompra(){
            Compra cp = new Compra();
            cp.setClave_c( txtNoCompra.getText().toString());
            cp.setFecha_c(txtFechaCompra.getText().toString());
            cp.setIVA(iva);
            cp.setSubtotal(subtotal);
            cp.setTotal(total);
            Proveedor pr = new Proveedor();
            pr.setId(idProveedor);
            cp.setProveedor(pr);


            DBCompra dbCompra = new DBCompra(getActivity());
            long id= dbCompra.insertarCompra(cp, detalleCompraArrayList, precioProms);


        }


}