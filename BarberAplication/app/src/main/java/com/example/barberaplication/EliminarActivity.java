package com.example.barberaplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EliminarActivity extends AppCompatActivity {

    ListView listMateriales;
    ArrayList<Material> listaMaterial = new ArrayList<>();
    SQLUtilities conexion;
    private ImageView Regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        listMateriales = findViewById(R.id.listaProductos);
        conexion = new SQLUtilities(this);
        SQLiteDatabase db = conexion.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Material", null);

        if (c.moveToFirst()) {
            do {
                Material material = new Material(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
                listaMaterial.add(material);
            } while (c.moveToNext());
        }

        MaterialListAdapter adapter = new MaterialListAdapter(listaMaterial);
        listMateriales.setAdapter(adapter);

        Regresar = findViewById(R.id.Regreso);
        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class MaterialListAdapter extends ArrayAdapter<Material> {

        public MaterialListAdapter(ArrayList<Material> materials) {
            super(EliminarActivity.this, 0, materials);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            final Material currentMaterial = getItem(position);

            TextView textViewInfo = convertView.findViewById(R.id.textViewId);
            textViewInfo.setText("\n" + "\n" + "\n" +" | ID: " + currentMaterial.getId() + "\n" +" | Nombre: " + currentMaterial.getNombre() + "\n" + " | Cantidad: " + currentMaterial.getCantidad() + "\n" + " | Tipo: " + currentMaterial.getTipo());

            Button btnDecrease = convertView.findViewById(R.id.btnDecrease);
            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cantidadActual = Integer.parseInt(currentMaterial.getCantidad());
                    if (cantidadActual > 0) {
                        cantidadActual--;
                        currentMaterial.setCantidad(String.valueOf(cantidadActual));
                        // Actualizamos la cantidad en la base de datos
                        String sql = "UPDATE Material SET cantidad=" + cantidadActual + " WHERE id=" + currentMaterial.getId();
                        SQLiteDatabase db = conexion.getWritableDatabase();
                        db.execSQL(sql);
                        // Notificamos al adaptador que los datos han cambiado
                        notifyDataSetChanged();
                    }
                }
            });

            Button btnIncrease = convertView.findViewById(R.id.btnIncrease);
            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cantidadActual = Integer.parseInt(currentMaterial.getCantidad());
                    cantidadActual++;
                    currentMaterial.setCantidad(String.valueOf(cantidadActual));
                    // Actualizamos la cantidad en la base de datos
                    String sql = "UPDATE Material SET cantidad=" + cantidadActual + " WHERE id=" + currentMaterial.getId();
                    SQLiteDatabase db = conexion.getWritableDatabase();
                    db.execSQL(sql);
                    // Notificamos al adaptador que los datos han cambiado
                    notifyDataSetChanged();
                }
            });

            Button btnDelete = convertView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Eliminar el elemento de la lista y de la base de datos
                    SQLiteDatabase db = conexion.getWritableDatabase();
                    db.delete("Material", "id=?", new String[]{currentMaterial.getId()});
                    listaMaterial.remove(position);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }

}
