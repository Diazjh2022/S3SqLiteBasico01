package com.example.s3sqlitebasico01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;

public class MainActivity extends AppCompatActivity {

    TextView textCodigo, textDescripcion, textPrecio;
    Button btnBuscar, btnGuardar, btnEditar, btnBorrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCodigo = findViewById(R.id.textCodigo);
        textDescripcion = findViewById(R.id.textDescripcion);
        textPrecio = findViewById(R.id.textPrecio);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);
    }

    public void guardar(View view) {
        ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = textCodigo.getText().toString();
        String descripcion = textDescripcion.getText().toString();
        String precio = textPrecio.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);
        bd.insert("articulos", null, registro);
        bd.close();
        textCodigo.setText("");
        textDescripcion.setText("");
        textPrecio.setText("");

        Toast.makeText(this, "Registrado Correctamente", Toast.LENGTH_SHORT).show();

    }

    public void buscar(View view) {
        ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();
        String codigo = textCodigo.getText().toString();

        Cursor fila = bd.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo='" + codigo + "'", null);

        if (fila.moveToFirst()) {
            textDescripcion.setText(fila.getString(0));
            textPrecio.setText(fila.getString(1));
            bd.close();
            Toast.makeText(this, "Producto encontrado", Toast.LENGTH_SHORT).show();

        } else {
            bd.close();
            Toast.makeText(this, "No se encuentra el producto", Toast.LENGTH_SHORT).show();
            textCodigo.setText("");
            textDescripcion.setText("");
            textPrecio.setText("");

        }

    }

    public void editar(View view) {
        ConexionBD conexion = new ConexionBD(this, "administracion", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = textCodigo.getText().toString();
        String descripcion = textDescripcion.getText().toString();
        String precio = textPrecio.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);

        int x =bd.update("articulos", registro ,"codigo='" + codigo + "'",null);
        if (x>0){
            Toast.makeText(this, "Modificado correctamente", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "NO se puede modificar", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}

