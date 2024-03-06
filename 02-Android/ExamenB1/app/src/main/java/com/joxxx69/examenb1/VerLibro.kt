package com.joxxx69.examenb1

import Libro
import Receta
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class VerLibro : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Receta>
    var managerLibro = BaseDatosMemoria;
    var idLibro= "";
    private var idItemSeleccionado = ""
    private val firestore = FirebaseFirestore.getInstance()


//    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_libro)


        val botonAgregarReceta = findViewById<Button>(R.id.id_btn_agregar_receta)
        // logica para agregar
        botonAgregarReceta.setOnClickListener{
            irActividad(FormReceta::class.java)
        }
        val botonRegresarLibros = findViewById<Button>(R.id.id_btn_libros_atras)
        botonRegresarLibros.setOnClickListener{
            finish()
        }
        idLibro = intent.getStringExtra("idLibro").toString()
        Log.d("VerLibro: ", "${idLibro}")



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

        val recetaId = Firestore.recetas[infoReceta.position].idReceta
        Log.d("onCreateContextMenu ver libro: ", "${recetaId}")

        if(recetaId != null){
            Log.d("ver libro on contex", "${recetaId}")
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
                val idReceta = idItemSeleccionado
                Log.d("eliminar Receta", "${idReceta}")
                if(idReceta.isNotEmpty()){
                    eliminarRecetaFs(idReceta)
                }
//                abrirDialogEliminarReceta()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    private fun eliminarRecetaFs(key: String) {
        val firebaseManager = Firestore()
        firebaseManager.eliminarReceta(key)
    }

//    fun abrirDialogEliminarReceta(){
//        val builderDialog = AlertDialog.Builder(this);
//        builderDialog.setTitle("Desea eliminar?");
//        builderDialog.setNegativeButton("No",null);
//        builderDialog.setPositiveButton("Si"){
//            dialog, _ ->
//            if(idItemSeleccionado.let { BaseDatosMemoria.eliminarReceta(idLibro,it) }){
//                actualizarlistViewRecetas()
//            }
//        }
//        val dialog = builderDialog.create();
//        dialog.show()
//    }


    fun actualizarlistViewRecetas(){
        Log.d("1- recetas", "1")
        val listViewRecetas = findViewById<ListView>(R.id.id_list_view_recetas)
        Log.d("2- recetas", "2")

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Firestore.recetas
        )
        listViewRecetas.adapter = adaptador;
        adaptador.notifyDataSetChanged()
        Log.d("3- recetas", "3")

        val fireStoreManager = Firestore();
        fireStoreManager.obtenerRecetasPoridLibro(idLibro){listRecetas ->
            Firestore.recetas.clear()
            Firestore.recetas.addAll(listRecetas)
            adaptador.notifyDataSetChanged()
        }
        Log.d("recetas ver", "${Firestore.recetas}")
        val firestore = FirebaseDatabase.getInstance()
        val recetasRef = firestore.reference.child("recetas")
        Log.i("fuera","1")

        recetasRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("dentro","1")
                Firestore.recetas.clear()
                Log.i("dentro","${snapshot}")
                for (childSnapshot in snapshot.children) {
                    Log.d("repeticion", "${childSnapshot}")
                    val nombre= childSnapshot.child("nombre").getValue(String::class.java)
                    val nacionalidad= childSnapshot.child("nacionalidad").getValue(String::class.java)
                    val tiempoPreparacion=childSnapshot.child("tiempoPreparacion").getValue(Int::class.java)
                    val ingredientes=childSnapshot.child("ingredientes").getValue(String::class.java)
                    val idLibro = childSnapshot.child("idLibro").getValue(String::class.java)
                    Log.d("idReceta: ", "${childSnapshot.key}")

                    val receta = Receta(
                        idReceta = childSnapshot.key,
                        nombre!!,
                        nacionalidad!!,
                        tiempoPreparacion!!,
                        ingredientes!!,
                        idLibro!!
                    )
                    val idLibroAux = intent.getStringExtra("idLibro")
                    if (idLibroAux.equals(receta.idLibro)){
                        Firestore.recetas.add(receta)

                    }
                }

                adaptador.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Manejar errores aquí
                Log.e("FirebaseError", "Error al obtener datos de Firebase: ${error.message}")
                Toast.makeText(
                    this@VerLibro,
                    "Error al obtener datos de Firebase",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        registerForContextMenu(listViewRecetas)

    }

    fun irActividad(clase:Class<*>){
        val intent = Intent(this,clase);
        intent.putExtra("idLibro",idLibro);
        Log.d("VerLibro 2: ", "${idLibro}")

        actualizarlistViewRecetas()
        startActivity(intent)
    }
    fun irActividad(clase:Class<*>,idReceta:String?,idLibro: String?){
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