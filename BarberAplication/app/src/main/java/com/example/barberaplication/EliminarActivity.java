package com.example.barberaplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EliminarActivity extends AppCompatActivity {

    ListView listMateriales;
    ArrayList<Material> listaMaterial = new ArrayList<Material>();
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

        ArrayList<String> listaInformacion = new ArrayList<>();
        for (Material material : listaMaterial) {
            listaInformacion.add("Nombre: " + material.getNombre() + "\n" + " | Cantidad: " + material.getCantidad() + "\n" + " | Tipo: " + material.getTipo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaInformacion);
        listMateriales.setAdapter(adapter);

        listMateriales.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder window = new AlertDialog.Builder(EliminarActivity.this);
                window.setTitle("Eliminar");
                window.setMessage("¿Está seguro?");
                window.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM Material WHERE id=" + listaMaterial.get(position).getId();
                        db.execSQL(sql);
                        Intent iThis = new Intent(EliminarActivity.this, EliminarActivity.class);
                        startActivity(iThis);
                        finish();
                    }
                });
                window.show();
                return false;
            }
        });

        Regresar = findViewById(R.id.Regreso);
        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
