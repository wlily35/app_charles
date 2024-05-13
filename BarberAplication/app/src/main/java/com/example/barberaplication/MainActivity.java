package com.example.barberaplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnReg;
    private Button btnBu;
    private Button btnEli;

    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReg = (Button)findViewById(R.id.btnReg);
        btnBu = (Button)findViewById(R.id.btnBu);

        btnEli = (Button)findViewById(R.id.btnEli);
        btnSalir = (Button)findViewById(R.id.btnSalir);

        btnReg.setOnClickListener(this);
        btnBu.setOnClickListener(this);

        btnEli.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
        SQLUtilities conexion = new SQLUtilities(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnReg) {
            Intent intent = new Intent(MainActivity.this, RegistrarMateriales.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btnBu) {
            Intent intent = new Intent(MainActivity.this, MostrarMateriales.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btnEli) {
            Intent intent = new Intent(MainActivity.this, EliminarActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btnSalir) {
            finish();
            System.exit(0);
        }

    }


    //Metodo para mostrar y ocultar el menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    //Metodo para asignar las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.opRegistrar){
            Intent intent = new Intent( MainActivity.this, RegistrarMateriales.class);
            startActivity(intent);

        }else if ( id == R.id.opMostrar ){
            Intent intent = new Intent( MainActivity.this, MostrarMateriales.class);
            startActivity(intent);

        }else if(id == R.id.opListar){
            Intent intent = new Intent( MainActivity.this, EliminarActivity.class);
            startActivity(intent);

        }else{
            finish();
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);

    }


}
