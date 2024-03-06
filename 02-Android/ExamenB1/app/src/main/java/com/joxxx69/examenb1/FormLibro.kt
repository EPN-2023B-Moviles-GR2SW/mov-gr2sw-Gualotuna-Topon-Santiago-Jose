package com.joxxx69.examenb1

import Libro
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        var libro = Libro(
            idLibro = intent.getStringExtra("idLibro"),
            titulo= tituloInput.text.toString(),
            autor= autorInput.text.toString(),
            anioPublicacion = anioPublicacionInput.text.toString().toInt(),
            editorial = editorialInput.text.toString()
        )
        val firestore = Firestore()
        val idLibro = intent.getStringExtra("idLibro");
        Log.d("guardar libro","${idLibro}")
        if(idLibro === null){
            firestore.agregarLibro(libro);
        }else{
            firestore.actualizarLibro(idLibro,libro)
//            managerLibro.actualizarlibro(idLibro,libro);
        }



    }
    fun llenarDatos(){
        val formTitulo = findViewById<TextView>(R.id.id_text_view_form_libro)
        val tituloInput =findViewById<EditText>(R.id.id_text_nombre);
        val autorInput =findViewById<EditText>(R.id.id_text_nacionalidad);
        val anioPublicacionInput =findViewById<EditText>(R.id.id_text_tiempo);
        val editorialInput =findViewById<EditText>(R.id.id_text_ingredientes);
        val idLibro = intent.getStringExtra("idLibro");

        Log.d("llenarDatos: ", "${idLibro}")
        if(idLibro.isNullOrEmpty()){
            return
        }

        if(!idLibro!!.isNullOrEmpty()){
            Log.d("Entro: ", "${idLibro}")
            val firestore = Firestore()
            firestore.buscarLibroPorId(idLibro){
                Log.d("libro Encontrado","${it!!.idLibro.toString()}")
                formTitulo.text = "Actualizar Libro"
                tituloInput.setText(it!!.titulo.toString());
                autorInput.setText(it!!.autor.toString());
                anioPublicacionInput.setText(it!!.anioPublicacion.toString())
                editorialInput.setText(it!!.editorial.toString())
            }

        }

    }
}