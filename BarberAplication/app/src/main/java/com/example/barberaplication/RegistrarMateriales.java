package com.example.barberaplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarMateriales extends AppCompatActivity implements View.OnClickListener{

    private Spinner spinner;
    private EditText edtId, edtNombre, edtCant;
    private Button btnRegistrar;
    private ImageView Regresar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtId = (EditText)findViewById(R.id.edtId);
        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtCant = (EditText)findViewById(R.id.edtCant);
        spinner = (Spinner)findViewById(R.id.spinner);
        btnRegistrar = (Button)findViewById(R.id.btnMostrar);
        Regresar = (ImageView)findViewById(R.id.Regreso);
        Regresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
        btnRegistrar.setOnClickListener(this);

        String [] opciones = {"Tipo", "Gel" , "Cremas de Peinar", "Cremas de Afeitar", "Pomade", "Brillantina", "Cera", "Spray", "Polvo de Peinado", "Shampoo","Laca","shampoo para Barba","Tonicos","Aceites","Mascarillas","kitsS" };




        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        spinner.setAdapter(adapter);
    }

    private void registrarMaterail(){
        SQLUtilities conexion = new SQLUtilities(this);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String id = edtId.getText().toString();
        String nombre = edtNombre.getText().toString();
        String cantidad = edtCant.getText().toString();
        String tipo = spinner.getSelectedItem().toString();

        // Verificar si el ID ya existe en la base de datos
        Cursor cursor = db.rawQuery("SELECT * FROM Material WHERE Id=?", new String[]{id});
        if(cursor.getCount() > 0) {
            // El ID ya existe, mostrar mensaje de error y salir de la función
            Toast.makeText(this, "El ID ya existe, por favor ingrese un ID diferente", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        cursor.close();

        if ( !id.isEmpty() && !nombre.isEmpty() && !cantidad.isEmpty() && !tipo.equals("Tipo") ){
            ContentValues registro = new ContentValues();

            registro.put("Id", id);
            registro.put("nombre", nombre);
            registro.put("cantidad", cantidad);
            registro.put("tipo", tipo);
            db.insert("Material", null, registro);
            db.close();
            Toast.makeText(this,nombre +" se ha registrado con exito", Toast.LENGTH_SHORT).show();
            edtId.setText("");
            edtNombre.setText("");
            edtCant.setText("");
            spinner.setSelection(0);


        }else{
            Toast.makeText(this," Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        registrarMaterail();

    }
}
