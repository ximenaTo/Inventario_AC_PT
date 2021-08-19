package com.ejercicio.inventario_ac_pt.ui.home;

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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ejercicio.inventario_ac_pt.BD.DBCliente;
import com.ejercicio.inventario_ac_pt.BD.DBCompra;
import com.ejercicio.inventario_ac_pt.BD.DBDetalleVenta;
import com.ejercicio.inventario_ac_pt.BD.DBProducto;
import com.ejercicio.inventario_ac_pt.BD.DBVendedor;
import com.ejercicio.inventario_ac_pt.BD.DBVenta;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Cliente;
import com.ejercicio.inventario_ac_pt.entidades.DetalleVenta;
import com.ejercicio.inventario_ac_pt.entidades.Producto;
import com.ejercicio.inventario_ac_pt.entidades.Vendedor;
import com.ejercicio.inventario_ac_pt.entidades.Venta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Spinner spnProductosV, spnVendedorV, spnClienteV;

    TextView txtClaveCV, txtCalleCliente, txtNoVenta, txtFechaVenta, txtCantidad;

    CheckBox chbFacturacion;

    Button btnAgregarPV,btnAltaV, btnBajaV, btnModifV, btnBuscarV, btnPDFV;

    TableLayout tblProductosV, tblTotalCalzado, tblTotalesImporte;

    int idCliente, idVendedor;

    ArrayList<Cliente> listaCliente;
    ArrayList<Vendedor> listaVendedor;
    ArrayList<Producto> listaProducto;
    ArrayList<DetalleVenta> detalleVenta;

    ArrayList<String> clientes=new ArrayList<String>();

    int cantidad = 0;
    float precioP = 0;
    int totalCan =0;
    float IVA = 0;
    float subtotal = 0;
    float total = 0;
    float sumImporte = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        spnProductosV = (Spinner) root.findViewById(R.id.cmbProductoV);
        spnVendedorV = (Spinner) root.findViewById(R.id.cmbVendedorV);
        spnClienteV = (Spinner) root.findViewById(R.id.cmbCliente);

        txtClaveCV = (TextView) root.findViewById(R.id.txtClaveC);
        txtCalleCliente =  (TextView) root.findViewById(R.id.txtCalleCliente);
        txtNoVenta =  (TextView) root.findViewById(R.id.txtNoVenta);
        txtFechaVenta = (TextView) root.findViewById(R.id.txtFechaVenta);
        txtCantidad = (TextView) root.findViewById(R.id.txtCantidad);

        chbFacturacion = (CheckBox) root.findViewById(R.id.chbFacturacion);

        btnAgregarPV = (Button)  root.findViewById(R.id.btnAgregarPV);
        btnAltaV = (Button) root.findViewById(R.id.btnAltaV);
        btnBajaV = (Button) root.findViewById(R.id.btnBajaV);
        btnModifV = (Button) root.findViewById(R.id.btnModiV);
        btnBuscarV = (Button) root.findViewById(R.id.btnBuscarV);
        btnPDFV = (Button) root.findViewById(R.id.btnPDFV);

        tblProductosV = (TableLayout) root.findViewById(R.id.tblProductosV);
        tblTotalCalzado = (TableLayout) root.findViewById(R.id.tblTotalesCalzado);
        tblTotalesImporte = (TableLayout) root.findViewById(R.id.tblTotalesImporte);

        listaClientes();
        listaVendedor();
        listaProductos();
        numeroCompra();
        fechaCompra();


        chbFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean factura = chbFacturacion.isChecked();

            }
        });
        spnClienteV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccion= spnClienteV.getSelectedItem().toString();
                if(seleccion != "Seleccione cliente"){
                    String[] parts = seleccion.split( " - ");
                    Cliente cliente;
                    DBCliente dbCliente = new DBCliente(getActivity());
                    cliente= dbCliente.buscarCliente(parts[0]);
                    txtClaveCV.setText(cliente.getClave_c());
                    txtCalleCliente.setText(cliente.getCalle_c());
                    idCliente = cliente.getId();
                }else{
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnVendedorV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccion= spnVendedorV.getSelectedItem().toString();
                if(seleccion != "Seleccione vendedor"){
                    String[] parts = seleccion.split( " - ");
                    Vendedor vend;
                    DBVendedor dbVendedor = new DBVendedor(getActivity());
                    vend= dbVendedor.buscarVendedor(parts[0]);
                    idVendedor = vend.getId();
                }else{

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnAgregarPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { agregarProducto();}});


        btnAltaV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { insertarVenta(detalleVenta);}});
         /*

        btnBajaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {eliminar(); }});

        btnModifP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { modificar(); }});



        btnBuscarV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palabra = txtNoVenta.getText().toString();
                buscarVenta(palabra);
            }
        });

          */

        return root;
    }

    private void agregarProducto(){
        String seleccion= spnProductosV.getSelectedItem().toString();
        String[] parts = seleccion.split( " - ");
        Producto producto;
        DBProducto dbProducto = new DBProducto(getActivity());
        producto= dbProducto.buscarProducto(parts[0]);

        String claveCliente = txtClaveCV.getText().toString();
        Cliente cliente;
        DBCliente dbCliente = new DBCliente(getActivity());
        cliente = dbCliente.buscarCliente(claveCliente);

        DetalleVenta dv = new DetalleVenta();
        Venta v = new Venta();
        detalleVenta = new ArrayList<>();

        cantidad = Integer.parseInt(txtCantidad.getText().toString());
        producto.setExistencia_p(String.valueOf(Integer.parseInt(producto.getExistencia_p()) - cantidad));
        precioP = producto.getPrecioCosto_p();
        totalCan = totalCan + cantidad;
        float totalRegistro = precioP * Float.parseFloat(txtCantidad.getText().toString());
        sumImporte = sumImporte + totalRegistro;

        if  (chbFacturacion.isChecked() == true){
            IVA = (sumImporte * 16) / 100;
        }

        dv.setClave_c(txtNoVenta.getText().toString());
        dv.setUnidad_ve("Par");
        dv.setCantidad_ve(cantidad);
        dv.setPrecio_ve(producto.getPrecioCosto_p());
        dv.setImporte_ve(precioP * Float.parseFloat(txtCantidad.getText().toString()));
        dv.setIdProducto_ve(producto.getId());
        dv.setProducto(producto);

        v.setClave_ve(txtNoVenta.getText().toString());
        v.setFecha_ve(txtFechaVenta.getText().toString());
        v.setCantidadT_ve(totalCan);
        v.setIVA(IVA);
        v.setSubtotal(sumImporte);
        v.setTotal(sumImporte + IVA);
        v.setIdProducto_v(producto.getId());
        v.setIdCliente_v(cliente.getId());
        v.setCliente(cliente);
        v.setProducto(producto);

        dv.setVenta(v);

        detalleVenta.add(dv);

        listaProductosVenta();
    }

    public void listaProductosVenta(){

        for(DetalleVenta DVenta: detalleVenta)
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
                if(i == 0){ textView.setText(DVenta.getVenta().getClave_ve()); }
                if(i == 1){ textView.setText(DVenta.getProducto().getNombre_p()); }
                if(i == 2){ textView.setText(DVenta.getUnidad_ve()); }
                if(i == 3){ textView.setText(DVenta.getProducto().getLinea_p()); }
                if(i == 4){ textView.setText(""+DVenta.getCantidad_ve()); }
                if(i == 5){ textView.setText(""+DVenta.getProducto().getPrecioCosto_p()); }
                if(i == 6){ textView.setText(""+DVenta.getImporte_ve()); }

                tableRow.addView(textView);
                totalProducto();
                totalMonetario();
            }
            tblProductosV.addView(tableRow);
        }

    }

    public void totalProducto(){

        for(DetalleVenta DVenta: detalleVenta)
        {
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < 1; i++) {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setTextColor(Color.BLACK);
                textView.setMinWidth(500);
                textView.setMaxWidth(550);
                textView.setGravity(Gravity.CENTER);
                if(i == 0){ textView.setText(""+DVenta.getVenta().getCantidadT_ve()); }

                tableRow.addView(textView);
            }
            tblTotalCalzado.removeViews(1,tblTotalCalzado.getChildCount()-1);
            tblTotalCalzado.removeViews(1, tblTotalCalzado.getChildCount()-1);
            tblTotalCalzado.removeViews(1, tblTotalCalzado.getChildCount()-1);
            tblTotalCalzado.addView(tableRow);
        }

    }

    public void totalMonetario(){

        for(DetalleVenta DVenta: detalleVenta)
        {
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < 4; i++) {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setTextColor(Color.BLACK);
                textView.setMinWidth(410);
                textView.setMaxWidth(420);
                textView.setGravity(Gravity.CENTER);
                if(i == 0){ textView.setText(""+DVenta.getVenta().getSubtotal()); }
                if(i == 1){ textView.setText(""+DVenta.getVenta().getIVA()); }
                if(i == 2){ textView.setText(""+DVenta.getVenta().getTotal()); }

                tableRow.addView(textView);
            }
            tblTotalesImporte.removeViews(1,tblTotalesImporte.getChildCount()-1);
            tblTotalesImporte.removeViews(1, tblTotalesImporte.getChildCount()-1);
            tblTotalesImporte.removeViews(1, tblTotalesImporte.getChildCount()-1);
            tblTotalesImporte.addView(tableRow);
        }

    }

    public void insertarVenta(ArrayList<DetalleVenta>listaDetalle){

        DBVenta dbVenta = new DBVenta(getActivity());
        DBDetalleVenta dbDVenta = new DBDetalleVenta(getActivity());

        String seleccion= spnProductosV.getSelectedItem().toString();
        String[] parts = seleccion.split( " - ");
        Producto producto;
        DBProducto dbProducto = new DBProducto(getActivity());
        producto= dbProducto.buscarProducto(parts[0]);

        String claveCliente = txtClaveCV.getText().toString();
        Cliente cliente;
        DBCliente dbCliente = new DBCliente(getActivity());
        cliente = dbCliente.buscarCliente(claveCliente);

        long idD= 0;


        long idV= dbVenta.insertarVenta(
                txtClaveCV.getText().toString(),
                txtFechaVenta.getText().toString(),
                totalCan,
                IVA,
                sumImporte,
                (sumImporte + IVA),
                producto.getId(),
                cliente.getId());

        if (idV >0){
            for (DetalleVenta dv: listaDetalle) {
                idD= dbDVenta.insertarDetalle(
                        dv.getVenta().getClave_ve(),
                        dv.getUnidad_ve(),
                        dv.getCantidad_ve(),
                        dv.getPrecio_ve(),
                        dv.getImporte_ve(),
                        dv.getIdProducto_ve(),
                        Integer.parseInt(""+idV));
            }
            System.out.println(""+idV);
            limpiarFinalizar();
        }

        if(idD >0){
            Toast.makeText(getActivity(), "venta guardada", Toast.LENGTH_LONG).show();
            //listaProductos(2);
            limpiarFinalizar();
        }else {
            Toast.makeText(getActivity(), "Error al guardar la venta", Toast.LENGTH_LONG).show();
        }

    }

    private void listaClientes(){
        DBCliente dbCliente = new DBCliente(getActivity());
        listaCliente = new ArrayList<>(dbCliente.listaClientes());
        ArrayList<String> arreglo = new ArrayList<>();
        arreglo.add("Seleccione cliente");
        for (Cliente cliente: listaCliente ) {
            arreglo.add(cliente.getClave_c() +" - "+ cliente.getNombre_c());
        }
        ArrayAdapter<String> proveedor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item , arreglo);
        spnClienteV.setAdapter(proveedor);
    }
    private void listaVendedor(){
        DBVendedor dbVendedor = new DBVendedor(getActivity());
        listaVendedor = new ArrayList<>(dbVendedor.listaVendedores());
        ArrayList<String> arreglo = new ArrayList<>();
        arreglo.add("Seleccione vendedor");
        for (Vendedor vendedor: listaVendedor ) {
            arreglo.add(vendedor.getClave() +" - "+ vendedor.getNombre());
        }
        ArrayAdapter<String> vendedor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item , arreglo);
        spnVendedorV.setAdapter(vendedor);
    }

    private void listaProductos(){
        DBProducto dbProducto = new DBProducto(getActivity());
        listaProducto = new ArrayList<>(dbProducto.listaProductos());
        ArrayList<String> arreglo = new ArrayList<>();
        arreglo.add("Seleccione producto");
        for (Producto producto: listaProducto ) {
            arreglo.add(producto.getClave_p() +" - "+ producto.getNombre_p());
        }
        ArrayAdapter<String> proveedor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item , arreglo);
        spnProductosV.setAdapter(proveedor);
    }

    private void numeroCompra(){
        DBVenta dbCotizacion = new DBVenta(getActivity());
        int numeroCompra = dbCotizacion.numeroVenta();
        numeroCompra = numeroCompra+1;
        txtNoVenta.setText(numeroCompra+"");
    }

    private void fechaCompra(){
        Date objDate = new Date();
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String fecha = objSDF.format(objDate);
            txtFechaVenta.setText(fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiarAgrear(){
        spnProductosV.setSelection(0);
        txtCantidad.setText("");
    }

    private void limpiarFinalizar(){
        txtNoVenta.setText("");
        spnClienteV.setSelection(0);
        txtClaveCV.setText("");
        txtCalleCliente.setText("");
        txtFechaVenta.setText("");
        spnVendedorV.setSelection(0);
        spnProductosV.setSelection(0);
        txtCantidad.setText("");
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

    private int consultarClientes(String cliente){
        DBCliente dbCliente = new DBCliente(getActivity());
        Cliente cli = new Cliente();
        cli = dbCliente.buscarCliente(cliente);
        int cursor = 0;
        clientes.add(""+cli);
        for (int i=0; i < clientes.size();i++){
            if (clientes.get(i).equals(cliente)){
                cursor = i;
                break;
            }
        }
        return cursor;

    }
}