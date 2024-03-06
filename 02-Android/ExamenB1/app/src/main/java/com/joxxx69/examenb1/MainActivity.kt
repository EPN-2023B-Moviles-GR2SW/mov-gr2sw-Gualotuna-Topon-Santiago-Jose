package com.joxxx69.examenb1

import Libro
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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Libro>;
    var idItemSeleccionado = "0"
    var idBorrar =0;



    //    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        actualizarLitViewLibros();

        val botonCrearLibro = findViewById<Button>(R.id.id_btn_agregar_libro)
        botonCrearLibro.setOnClickListener{
            irActividad(FormLibro::class.java)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_libro, menu);
        val infoLibro = menuInfo as AdapterView.AdapterContextMenuInfo
        val libroId = Firestore.libros[infoLibro.position].idLibro
        Log.d("onCreateContextMenu: ", "${libroId}")
        if(libroId != null){
            idItemSeleccionado = libroId
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_ver_libro -> {
                try {
                    val idLibro = idItemSeleccionado;
                    irActividad(VerLibro::class.java,idLibro.toString())
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
                val idLibro = idItemSeleccionado
                if(idLibro.isNotEmpty()){
                    eliminarLibroFs(idLibro)
                }
                return true;
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }

    fun irActividad(clase: Class<*>, id:String?){
        val intent = Intent(this,clase)
        intent.putExtra("idLibro",id)
        startActivity(intent)
    }




    // ---- Actualizacion de la lista ---

    fun actualizarLitViewLibros(){

        val listViewLibros = findViewById<ListView>(R.id.id_list_view_libros)

//        Firestore.libros.mapIndexed { index, libro ->
//            "${index}: ${libro.titulo}"
//        }
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Firestore.libros
        )
        listViewLibros.adapter = adaptador;

        val fireStoreManager = Firestore();
        fireStoreManager.obtenerTodosLosLibros{listLibros ->
            Firestore.libros.clear()
            Firestore.libros.addAll(listLibros)
            adaptador.notifyDataSetChanged()
        }

        val firestore = FirebaseDatabase.getInstance()
        val librosRef = firestore.reference.child("libros")
        librosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Firestore.libros.clear()
                Log.i("dentro","${snapshot}")
                snapshot.children.forEachIndexed { index, childSnapshot ->
                    val titulo= childSnapshot.child("titulo").getValue(String::class.java)
                    val autor= childSnapshot.child("autor").getValue(String::class.java)
                    val anioPublicacion=childSnapshot.child("anioPublicacion").getValue(Int::class.java)
                    val editorial=childSnapshot.child("editorial").getValue(String::class.java)
                    Log.d("idLibro: ", "${childSnapshot.key}")
                    val libro = Libro(
                        idLibro = childSnapshot.key,
                        titulo!!,
                        autor!!,
                        anioPublicacion!!,
                        editorial!!
                    )

                    Firestore.libros.add(libro)
                }
                adaptador.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Manejar errores aquí
                Log.e("FirebaseError", "Error al obtener datos de Firebase: ${error.message}")
                Toast.makeText(
                    this@MainActivity,
                    "Error al obtener datos de Firebase",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        registerForContextMenu(listViewLibros)

    }

    private fun eliminarLibroFs(key: String) {
        val firebaseManager = Firestore()
        firebaseManager.eliminarLibro(key)
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