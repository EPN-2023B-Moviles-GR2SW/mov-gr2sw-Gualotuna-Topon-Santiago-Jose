package com.joxxx69.examenb1

import Libro
import Receta
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class Firestore {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    companion object{
        val libros = mutableListOf<Libro>()
        val recetas = mutableListOf<Receta>()
    }

    // Obtener todos los libros
    fun obtenerTodosLosLibros(callback: (List<Libro>) -> Unit) {
        val librosRef = database.child("libros")
        librosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val libros: MutableList<Libro> = mutableListOf()
                for (libroSnapshot in dataSnapshot.children) {
                    val libro = libroSnapshot.getValue(Libro::class.java)
                    libro?.let {
                        libros.add(it)
                    }
                }
                callback(libros)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error al obtener todos los libros: ${databaseError.message}")
                callback(emptyList())
            }
        })
    }

    // Agregar Libros
    fun agregarLibro(libro: Libro) {
        val libroRef = database.child("libros").push()
        libroRef.setValue(libro)
            .addOnSuccessListener {
                println("Libro agregado exitosamente: $libro")
            }
            .addOnFailureListener { exception ->
                println("Error al agregar el libro: ${exception.message}")
            }
    }

    //Eliminar libro
    fun eliminarLibro(key: String) {
        Log.d("key:","${key}")
        val libroRef = database.child("libros").child(key)
        libroRef.removeValue()
    }

    // buscar por id

    fun buscarLibroPorId(key: String, callback: (Libro?) -> Unit) {
        Log.d("BuscarPorId: ", "${key}")
        val libroRef = database.child("libros").child(key)
        libroRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Verificar si el libro existe
                if (snapshot.exists()) {
                    // Obtener los datos del libro
                    val libro = snapshot.getValue(Libro::class.java)
                    val libroDb = Libro(
                        libro!!.idLibro,
                        libro!!.titulo,
                        libro!!.autor,
                        libro!!.anioPublicacion,
                        libro!!.editorial
                    )
                    // Llamar al callback con el libro encontrado
                    callback(libroDb)
                } else {
                    // Llamar al callback con null si el libro no se encuentra
                    callback(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si la búsqueda es cancelada
                Log.e("Firebase Error", "Error searching for book: ${error.message}")
                // Llamar al callback con null en caso de error
                callback(null)
            }
        })


    }

    fun actualizarLibro(key: String, libroNuevo: Libro) {
        // Obtener una referencia al libro que se va a actualizar
        val libroRef = database.child("libros").child(key)

        libroRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val libro = dataSnapshot.getValue(Libro::class.java)
                if (libro != null) {
                    println("Libro actual antes de la actualización: $libro")
                    libroRef.setValue(libroNuevo) // Actualizar el libro en la base de datos
                    println("Libro actualizado: $libroNuevo")
                } else {
                    println("Libro no encontrado con el índice $key")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error al leer el libro con el índice $key: ${databaseError.message}")
            }
        })
    }


    //---------------- Recetas

    // Agregar recetas
    fun agregarReceta(receta: Receta) {
        val recetaRef = database.child("recetas").push()
        recetaRef.setValue(receta)
            .addOnSuccessListener {
                println("Libro agregado exitosamente: $receta")
            }
            .addOnFailureListener { exception ->
                println("Error al agregar el libro: ${exception.message}")
            }
    }

    // obtener recetas
    fun obtenerRecetasPoridLibro(idLibro: String, callback: (List<Receta>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val recetasRef = database.getReference("recetas")

        val query: Query = recetasRef.orderByChild("idLibro").equalTo(idLibro)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val recetas: MutableList<Receta> = mutableListOf()
                    for (recetaSnapshot in dataSnapshot.children) {
                        val receta = recetaSnapshot.getValue(Receta::class.java)
                        println("Receta encontrada: ${receta?.nombre}")
                        receta?.let {
                            recetas.add(it)
                        }
                    }
                    callback(recetas)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error al obtener recetas por nombre: ${databaseError.message}")
                callback(emptyList())
            }
        })
    }

    //Eliminar receta
    fun eliminarReceta(key: String) {
        Log.d("key:","${key}")
        val recetaRef = database.child("recetas").child(key)
        recetaRef.removeValue()
    }

    //actualizar receta

    fun actualizarReceta(key: String, recetaNueva: Receta) {
        // Obtener una referencia al libro que se va a actualizar
        val recetaRef = database.child("recetas").child(key)

        recetaRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val receta = dataSnapshot.getValue(Receta::class.java)
                if (receta != null) {
                    println("Libro actual antes de la actualización: $receta")
                    recetaRef.setValue(recetaNueva) // Actualizar el libro en la base de datos
                    println("Libro actualizado: $recetaNueva")
                } else {
                    println("Libro no encontrado con el índice $key")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error al leer el libro con el índice $key: ${databaseError.message}")
            }
        })
    }

    // buscar por id

    fun buscarRecetaPorId(key: String, callback: (Receta?) -> Unit) {
        Log.d("BuscarPorId: ", "${key}")
        val recetaRef = database.child("recetas").child(key)
        recetaRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Verificar si el libro existe
                if (snapshot.exists()) {
                    // Obtener los datos del libro
                    val receta = snapshot.getValue(Receta::class.java)
                    val recetaDb = Receta(
                        receta!!.idReceta,
                        receta!!.nombre,
                        receta!!.nacionalidad,
                        receta!!.tiempoPreparacion,
                        receta!!.ingredientes,
                        receta!!.idLibro
                    )
                    // Llamar al callback con el libro encontrado
                    callback(recetaDb)
                } else {
                    // Llamar al callback con null si el libro no se encuentra
                    callback(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si la búsqueda es cancelada
                Log.e("Firebase Error", "Error searching for book: ${error.message}")
                // Llamar al callback con null en caso de error
                callback(null)
            }
        })


    }


}