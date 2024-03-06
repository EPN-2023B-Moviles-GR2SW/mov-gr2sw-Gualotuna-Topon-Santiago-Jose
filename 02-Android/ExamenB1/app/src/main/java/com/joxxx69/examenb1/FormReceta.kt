package com.joxxx69.examenb1

import Receta
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class FormReceta : AppCompatActivity() {
    var managerLibro = BaseDatosMemoria;
    var firestore = Firestore()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_receta)
        llenarDatos();
        val botonGuardarReceta = findViewById<Button>(R.id.id_btn_guardar_receta)
        botonGuardarReceta.setOnClickListener{
            guardarReceta()
            finish()
//            val nuevoIntent = Intent(this,VerLibro::class.java);
//            startActivity(nuevoIntent);
        }

        val botonCancelarGuardado = findViewById<Button>(R.id.id_btn_cancelar_receta)
        botonCancelarGuardado.setOnClickListener{
            finish()
        }

    }

    fun guardarReceta(){
        val nombreInput = findViewById<EditText>(R.id.id_text_nombre)
        val nacionalidadInput = findViewById<EditText>(R.id.id_text_nacionalidad)
        val tiempoInput = findViewById<EditText>(R.id.id_text_tiempo)
        val ingredientesInput = findViewById<EditText>(R.id.id_text_ingredientes)
        val idLibro = intent.getStringExtra("idLibro");
        val idReceta = intent.getStringExtra("idReceta");
        Log.d("idLibro receta", "${idLibro}")
        Log.d("idReceta receta", "${idReceta}")
        var receta = Receta(
            idReceta,
            nombre = nombreInput.text.toString(),
            nacionalidad = nacionalidadInput.text.toString(),
            tiempoPreparacion = tiempoInput.text.toString().toInt(),
            ingredientes = ingredientesInput.text.toString(),
            idLibro
        )
        Log.d("antes receta", "${receta.toString()}")

//        if(idLibro!!.isNotEmpty() && idReceta!!.isNotEmpty()){
//            Log.d("actualizar Receta", "${3}")
//            managerLibro.actualizarReceta(idLibro,idReceta, receta);
//            setResult(RESULT_OK);
//        }else{
        if (idReceta  === null){
            Log.d("agregar Receta:", "${receta.toString()}")
            firestore.agregarReceta(receta)
        }else{
            firestore.actualizarReceta(idReceta,receta)
        }



            setResult(RESULT_OK);
//        }
    }

    fun llenarDatos(){

        val formTitulo = findViewById<TextView>(R.id.id_text_view_form_receta)
        val nombreInput = findViewById<EditText>(R.id.id_text_nombre)
        val nacionalidadInput = findViewById<EditText>(R.id.id_text_nacionalidad)
        val tiempoInput = findViewById<EditText>(R.id.id_text_tiempo)
        val ingredientesInput = findViewById<EditText>(R.id.id_text_ingredientes)
        val pasosInput = findViewById<EditText>(R.id.id_text_pasos)
        val idLibro = intent.getStringExtra("idLibro");
        val idReceta = intent.getStringExtra("idReceta");

        if(idReceta.isNullOrEmpty()){
            return
        }
        if(!idReceta!!.isNullOrEmpty()){
            val firestore = Firestore()
            firestore.buscarRecetaPorId(idReceta){
                formTitulo.text = "Actualizar Receta"
                nombreInput.setText(it!!.nombre.toString())
                nacionalidadInput.setText(it!!.nacionalidad.toString())
                tiempoInput.setText(it!!.tiempoPreparacion.toString())
                ingredientesInput.setText(it!!.ingredientes.toString())
            }


//            ingredientesInput.setText(receta.ingredientes.joinToString(separator = ",").toString())
//            pasosInput.setText(receta.pasos.joinToString(separator = ",").toString())
        }

    }
}