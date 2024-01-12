import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File


class LibroManager {
    private val libros = ArrayList<Libro>();
    private val objectMapper =  ObjectMapper();
    private val archivo:String = "data.json";


    fun agregarLibro(libro:Libro){
        libros.add(libro);

    }

    fun obtenerLibros(){
        for (idx in libros.indices){
            println("${idx} - ${libros.get(idx)}");
        }
    }

    fun mostrarRecetas(libro:Libro){
        for(idx in libro.recetas.indices){
            println("${idx}: ${libro.recetas.get(idx)}");

        }
    }

    fun obtenerLibroPorTitulo(titulo:String):Libro?{
        return libros.find { it.titulo.equals(titulo,ignoreCase = true) }
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

    fun buscarLibroPorId(idLibro: Int):Libro?{
        val libro = libros.getOrNull(idLibro);
        if(libro != null){
            return libro
        }else{
            println("Libro no encontrado con el indice ${idLibro}");
            return null;
        }
    }

    fun eliminarLibro(id:Int){
        val libro = libros.getOrNull(id);
        if(libro != null){
            libros.remove(libro);
            println("Libro eliminado: $libro");
        }else{
            println("Libro no encontrado con el indice ${id}");
        }
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
    fun eliminarReceta(idLibro:Int, idReceta:Int ){
        val libro = libros.getOrNull(idLibro);
        if(libro != null){
            val receta = libro.recetas.getOrNull(idReceta);
            if(receta != null){
                libro.recetas.remove(receta);
                println("Receta '${receta.nombre}' eliminada del libro '${libro.titulo}'.")
            }else{
                println("Receta no encontrada con el id ${idReceta}'.")
            }
        }else{
            println("Libro no encontrado con el id  ${idLibro}");
        }
    }


    // Funciones para guardar y leer un archivo

    fun tieneContenidoJson(): Boolean {
        val data = File(archivo)
        return data.exists() && data.length() > 0
    }

    fun guardarJsonEnArchivov2(listLibro: ArrayList<Libro>){
        val json = objectMapper.writeValueAsString(listLibro);
        File(archivo).writeText(json);
    }



    fun leerJsonDesdeArchivoV2(): List<Libro>{
        if(!tieneContenidoJson()){return listOf()}
        val data = File(archivo).readText();
        val objectData:List<Libro> = objectMapper.readValue(data, object : TypeReference<List<Libro>>() {});
        return objectData;
    }

    // Getter
    fun getLibros(): ArrayList<Libro> {
        return libros
    }

    // Setter
    fun setLibros(listaNueva: List<Libro>) {
        libros.clear();
        libros.addAll(listaNueva);
    }




}