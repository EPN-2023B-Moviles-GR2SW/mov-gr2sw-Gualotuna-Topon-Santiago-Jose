package com.joxxx69.examenb1

import Libro
import Receta
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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class VerLibro : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<String>
    lateinit var recetas:MutableList<Receta>;
    var managerLibro = BaseDatosMemoria;
    var idLibro=-1;
    private var idItemSeleccionado = -1


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_libro)

//        actualizarlistViewRecetas();

        val botonAgregarReceta = findViewById<Button>(R.id.id_btn_agregar_receta)
        // logica para agregar
        botonAgregarReceta.setOnClickListener{
            irActividad(FormReceta::class.java)
        }
        val botonRegresarLibros = findViewById<Button>(R.id.id_btn_libros_atras)
        botonRegresarLibros.setOnClickListener{
            finish()
        }
        idLibro = intent.getIntExtra("idLibro",-1)

        actualizarlistViewRecetas();



    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu_receta, menu)
        val infoReceta = menuInfo as AdapterView.AdapterContextMenuInfo;
        val recetaId = infoReceta.position;
        if(recetaId != null){
            idItemSeleccionado = recetaId;
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_editar_receta->{
                try{
                    val idReceta = idItemSeleccionado;
                    irActividad(FormReceta::class.java,idReceta,idLibro)
                }catch (e:Throwable){

                }
                return true;
            }
            R.id.menu_eliminar_receta ->{
                abrirDialogEliminarReceta()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogEliminarReceta(){
        val builderDialog = AlertDialog.Builder(this);
        builderDialog.setTitle("Desea eliminar?");
        builderDialog.setNegativeButton("No",null);
        builderDialog.setPositiveButton("Si"){
            dialog, _ ->
            if(idItemSeleccionado.let { BaseDatosMemoria.eliminarReceta(idLibro,it) }){
                actualizarlistViewRecetas()
            }
        }
        val dialog = builderDialog.create();
        dialog.show()
    }

    fun actualizarlistViewRecetas(){
        var libro =  managerLibro.buscarLibroPorId(idLibro)!!;
        recetas = managerLibro.obtenerRecetas(libro);
        val listViewRecetas = findViewById<ListView>(R.id.id_list_view_recetas)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            recetas.mapIndexed { index, receta ->
                "${index}: Receta de ${receta.nombre}"
            }
        )
        listViewRecetas.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listViewRecetas);
    }

    fun irActividad(clase:Class<*>){
        val intent = Intent(this,clase);
        intent.putExtra("idLibro",idLibro);
        actualizarlistViewRecetas()
        startActivity(intent)
    }
    fun irActividad(clase:Class<*>,idReceta:Int?,idLibro: Int?){
        val intent = Intent(this,clase);
        intent.putExtra("idReceta",idReceta);
        intent.putExtra("idLibro",idLibro)
        actualizarlistViewRecetas()
        startActivity(intent)
    }


    override fun onRestart() {
        super.onRestart()
        actualizarlistViewRecetas()
    }

    override fun onResume() {
        super.onResume()
        actualizarlistViewRecetas()
    }
}