data class Receta(
    var idReceta: String? =null,
    var nombre: String="",
    var nacionalidad: String="",
    var tiempoPreparacion:Int=0,
    var ingredientes: String ="",
    var idLibro: String? = null
){
    override fun toString(): String {
        return "${nombre}"
    }
}
