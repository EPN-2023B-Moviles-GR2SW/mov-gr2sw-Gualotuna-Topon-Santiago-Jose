package com.joxxx69.examenb1

import Libro
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class FormLibro : AppCompatActivity() {
    var managerLibro = BaseDatosMemoria;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_libro)

        //llenar los datos
        llenarDatos();
        val botonGuardarLibro = findViewById<Button>(R.id.id_btn_guardar)
        botonGuardarLibro.setOnClickListener {
            try {
                guardarLibro();
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
            val nuevoIntent = Intent(this, MainActivity::class.java)
            startActivity(nuevoIntent)
        }

        val botonCancelarLibro = findViewById<Button>(R.id.id_btn_libro_cancelar)
        botonCancelarLibro.setOnClickListener { finish() }


    }


    fun guardarLibro(){
        val tituloInput =findViewById<EditText>(R.id.id_text_nombre);
        val autorInput =findViewById<EditText>(R.id.id_text_nacionalidad);
        val anioPublicacionInput =findViewById<EditText>(R.id.id_text_tiempo);
        val editorialInput =findViewById<EditText>(R.id.id_text_ingredientes);
        val idLibro = intent.getIntExtra("idLibro",-1);
        var libro = Libro(
            titulo= tituloInput.text.toString(),
            autor= autorInput.text.toString(),
            anioPublicacion = anioPublicacionInput.text.toString().toInt(),
            editorial = editorialInput.text.toString()
        )
        if(idLibro == -1){
            managerLibro.agregarLibro(libro);
        }else{
            managerLibro.actualizarlibro(idLibro,libro);
        }



    }
    fun llenarDatos(){
        val formTitulo = findViewById<TextView>(R.id.id_text_view_form_libro)
        val tituloInput =findViewById<EditText>(R.id.id_text_nombre);
        val autorInput =findViewById<EditText>(R.id.id_text_nacionalidad);
        val anioPublicacionInput =findViewById<EditText>(R.id.id_text_tiempo);
        val editorialInput =findViewById<EditText>(R.id.id_text_ingredientes);
        val idLibro = intent.getIntExtra("idLibro",-1);
        if(idLibro != -1){
            val libroDB = managerLibro.buscarLibroPorId(idLibro);
            if(libroDB!==null){
                formTitulo.text = "Actualizar Libro"
                tituloInput.setText(libroDB.titulo.toString());
                autorInput.setText(libroDB.autor.toString());
                anioPublicacionInput.setText(libroDB.anioPublicacion.toString())
                editorialInput.setText(libroDB.editorial.toString())
            }
        }

    }
}