data class Receta(
    val nombre: String="",
    val nacionalidad: String="",
    val tiempoPreparacion:Int=0,
    val ingredientes: MutableList<String> = arrayListOf(),
    val pasos: MutableList<String> = arrayListOf(),
)
