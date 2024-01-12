package com.joxxx69.examenb1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<String>;
    var idItemSeleccionado = 0
    var idBorrar =0;


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCrearLibro = findViewById<Button>(R.id.id_btn_agregar_libro)
        botonCrearLibro.setOnClickListener{
            irActividad(FormLibro::class.java)
        }
        //adaptador
        actualizarLitViewLibros();
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater.inflate(R.menu.menu_libro, menu);
        val infoLibro = menuInfo as AdapterView.AdapterContextMenuInfo;
        val libroId = infoLibro.position
        if(libroId != null){
            idItemSeleccionado = libroId
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_ver_libro -> {
                try {
                    val idLibro = idItemSeleccionado;
                    irActividad(VerLibro::class.java,idLibro)
                }catch (e:Throwable){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }
            R.id.menu_editar_libro-> {
                try {
                    val idLibro = idItemSeleccionado;
                    irActividad(FormLibro::class.java,idLibro)
                }catch (e:Throwable){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }
            R.id.menu_eliminar_libro -> {
                    abrirDialogEliminarLibro()
                return true;
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }

    fun irActividad(clase: Class<*>, id:Int?){
        val intent = Intent(this,clase)
        intent.putExtra("idLibro",id)
        startActivity(intent)
    }


    // --- dialogo de eliminar libro ---

    fun abrirDialogEliminarLibro(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("Desea eliminar?")
        builderDialog.setNegativeButton("No",null);
        builderDialog.setPositiveButton("Si"){
            dialog,_ ->
            if(idItemSeleccionado.let { BaseDatosMemoria.eliminarLibro(it) }){
                actualizarLitViewLibros()
            }
        }
        val dialog = builderDialog.create();
        dialog.show();
    }


    // ---- Actualizacion de la lista ---

    fun actualizarLitViewLibros(){
        val listViewLibros = findViewById<ListView>(R.id.id_list_view_libros)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BaseDatosMemoria.libros.mapIndexed { index, libro ->
                "${index}: ${libro.titulo}"
            }
        )
        listViewLibros.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listViewLibros)
    }


    override fun onRestart() {
        super.onRestart()
        actualizarLitViewLibros()
    }

    override fun onResume() {
        super.onResume()
        actualizarLitViewLibros()
    }

}