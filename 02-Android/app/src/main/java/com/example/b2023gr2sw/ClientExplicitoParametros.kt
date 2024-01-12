package com.example.b2023gr2sw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ClientExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_explicito_parametros)
        val nombre = intent.getStringExtra("nombre");
        val apellido = intent.getStringExtra("apellido");
        val edad = intent.getIntExtra("edad",0);
        val boton = findViewById<Button>(R.id.btn_developer_respuesta)
        boton.setOnClickListener { developerRespuesta() }


    }
    fun developerRespuesta(){
        val intentDeveloperParametros = Intent();
        intentDeveloperParametros.putExtra("nombreModificado","Jose")
        intentDeveloperParametros.putExtra("edadModificada",23)
        setResult(RESULT_OK, intentDeveloperParametros)
        finish()
    }
}