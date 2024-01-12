package com.joxxx69.examenb1

import Libro
import Receta

class BaseDatosMemoria {
    companion object{
        val libros = ArrayList<Libro>();

        //------ Metodos de CRUD Libro --------

        fun agregarLibro(libro:Libro){
            libros.add(libro);

        }

        fun actualizarlibro(id:Int, libroNuevo:Libro){
            val libro = libros.getOrNull(id);
            if(libro != null){
                println("Libro actual antes de la actualización: $libro")
                libros[id] = libroNuevo
                println("Libro actualizado: $libroNuevo")
            }else{
                println("Libro no encontrado con el indice ${id}")
            }

        }

        fun eliminarLibro(id:Int):Boolean{
            val libro = libros.getOrNull(id);
            if(libro != null){
                libros.remove(libro);
                println("Libro eliminado: $libro");
                return true;
            }else{
                println("Libro no encontrado con el indice ${id}");
                return false;
            }
        }

        fun buscarLibroPorId(idLibro: Int):Libro?{
            val libro = libros.getOrNull(idLibro);
            if(libro != null){
                return libro
            }else{
                println("Libro no encontrado con el indice ${idLibro}");
                return null;
            }
        }



        //----- CRUD de recetas

        fun obtenerRecetas(libro:Libro):MutableList<Receta>{
            var listRecetas: MutableList<Receta> = mutableListOf();
            for(idx in libro.recetas.indices){
                println("${idx}: ${libro.recetas.get(idx)}");
                listRecetas.add(libro.recetas.get(idx))
            }
            return listRecetas;
        }

        fun agregarReceta(idLibro:Int, receta:Receta){
            val libro = libros.getOrNull(idLibro);
            if(libro != null){
                libro.recetas.add(receta);
                println("Receta agregada al libro '${libro.titulo}'.")
            }else{
                println("Libro no encontrado con el  ${idLibro}");
            }
        }

        fun actualizarReceta(idLibro: Int,idReceta: Int,recetaNew: Receta){
            val libro = libros.getOrNull(idLibro);
            if(libro != null){
                val receta = libro.recetas.getOrNull(idReceta);
                if(receta != null){
                    libro.recetas[idReceta] = recetaNew;
                }else{
                    println("Receta no encontrada con el id ${idReceta}'.")
                }
            }else{
                println("Libro no encontrado con el id  ${idLibro}");
            }
        }


        fun eliminarReceta(idLibro:Int, idReceta:Int ):Boolean{
            val libro = libros.getOrNull(idLibro);
            if(libro != null){
                val receta = libro.recetas.getOrNull(idReceta);
                if(receta != null){
                    libro.recetas.remove(receta);
                    println("Receta '${receta.nombre}' eliminada del libro '${libro.titulo}'.")
                    return true;
                }else{
                    println("Receta no encontrada con el id ${idReceta}'.")
                    return false;
                }
            }else{
                println("Libro no encontrado con el id  ${idLibro}");
                return false;
            }
        }


        //----- datos en memoria -----

        init {
            libros.add(
                Libro(
                    "MasterChef",
                    "Santiago",
                    2023,
                    "Salamandra",
                    recetas = mutableListOf(
                        Receta(
                            "encebollado mixto",
                            "ecuatoriana",
                            40,
                            mutableListOf(
                                "queso","pez", "aji"
                            ),
                            mutableListOf("cocinar","mezclar","hornear")
                        ),
//                        Receta(
//                            "encebollado mixto",
//                            "ecuatoriana",
//                            40,
//                            mutableListOf(
//                                "queso","pez", "aji"
//                            ),
//                            mutableListOf("cocinar","mezclar","hornear")
//                        ),
//                        Receta(
//                            "Camaron apanado",
//                            "ecuatoriana",
//                            40,
//                            mutableListOf(
//                                "queso","pez", "aji"
//                            ),
//                            mutableListOf("cocinar","mezclar","hornear")
//                        ),
//                        Receta(
//                            "Ceviche mixto",
//                            "ecuatoriana",
//                            40,
//                            mutableListOf(
//                                "queso","pez", "aji"
//                            ),
//                            mutableListOf("cocinar","mezclar","hornear")
//                        )
                    )
                )
            )
            libros.add(
                Libro(
                    "Pizzas",
                    "Jose",
                    2015,
                    "Santillana",
                    recetas = mutableListOf()
                )
            )
            libros.add(
                Libro(
                    "Mexicana",
                    "Simon",
                    2019,
                    "Cocito",
                    recetas = mutableListOf()
                )

            )
        }
    }
}