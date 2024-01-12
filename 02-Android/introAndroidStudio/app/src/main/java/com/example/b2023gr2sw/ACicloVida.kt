package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal:String="";

    fun mostrarSnackar(texto:String){
        textoGlobal+= texto;
        val snack = Snackbar.make(findViewById(R.id.cl_ciclo_vida), textoGlobal,Snackbar.LENGTH_INDEFINITE);
        snack.show();
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackar("hola");
        mostrarSnackar("OnCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackar("onStart")
    }
    override fun onResume() {
        super.onResume()
        mostrarSnackar("onResume")

    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackar("onRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackar("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.run {
            //GUARDAR LAS VARIABLES
            //PRIMITIVAS
            putString("textoGuardado",textoGlobal)

        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        //RECUPARAR LAS VARIABLES
        //PRIMITIVOS
        val textoRecuperado:String? = savedInstanceState
            .getString("textoGuardado");

        if (textoRecuperado!=null){
            mostrarSnackar(textoRecuperado)
            textoGlobal = textoRecuperado;
        }
    }
}