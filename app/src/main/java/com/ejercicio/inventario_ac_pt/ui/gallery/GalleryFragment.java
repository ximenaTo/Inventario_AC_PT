package com.ejercicio.inventario_ac_pt.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ejercicio.inventario_ac_pt.BD.DBCompra;
import com.ejercicio.inventario_ac_pt.BD.DBProveedor;
import com.ejercicio.inventario_ac_pt.BD.DBVendedor;
import com.ejercicio.inventario_ac_pt.R;
import com.ejercicio.inventario_ac_pt.entidades.Proveedor;
import com.ejercicio.inventario_ac_pt.entidades.Vendedor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    Spinner productosC, vendedorC,proveedorC ;

    TextView txtClaveProv, txtCalleCompra, txtNoCompra, txtFechaCompra;

    CheckBox chbFacturacion;

    Button btnAgregarPC;

    int idProveedor, idVendedor;

    ArrayList<Proveedor> listaProveedor;
    ArrayList<Vendedor> listaVendedor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        productosC = (Spinner) root.findViewById(R.id.cmbProductoC);
        vendedorC = (Spinner) root.findViewById(R.id.cmbVendedorC);
        proveedorC = (Spinner) root.findViewById(R.id.cmbProveedorC);

        txtClaveProv = (TextView) root.findViewById(R.id.txtClaveProv);
        txtCalleCompra =  (TextView) root.findViewById(R.id.txtCalleCompra);
        txtNoCompra =  (TextView) root.findViewById(R.id.txtNoCompra);
        txtFechaCompra = (TextView) root.findViewById(R.id.txtFechaCompra);

        chbFacturacion = (CheckBox) root.findViewById(R.id.chbFacturacion);

        btnAgregarPC = (Button)  root.findViewById(R.id.btnAgregarPC);

        listaProveedor();
        listaVendedor();
        numeroCompra();
        fechaCompra();


        chbFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean factura = chbFacturacion.isChecked();

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
        vendedorC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccion=vendedorC.getSelectedItem().toString();
                String[] parts = seleccion.split( " - ");
                Vendedor vend;
                DBVendedor dbVendedor = new DBVendedor(getActivity());
                vend= dbVendedor.buscarVendedor(parts[0]);
                idVendedor = vend.getId();


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
    private void listaVendedor(){
        DBVendedor dbVendedor = new DBVendedor(getActivity());
        listaVendedor = new ArrayList<>(dbVendedor.listaVendedores());
        ArrayList<String> arreglo = new ArrayList<>();
        for (Vendedor vendedor: listaVendedor ) {
            arreglo.add(vendedor.getClave() +" - "+ vendedor.getNombre());
        }
        ArrayAdapter<String> vendedor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item , arreglo);
        vendedorC.setAdapter(vendedor);
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
}