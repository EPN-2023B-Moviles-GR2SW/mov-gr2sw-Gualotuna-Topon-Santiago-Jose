import androidx.compose.ui.text.toUpperCase

data class Libro(
    var idLibro: String?=null,
    var titulo: String="",
    var autor: String="",
    var anioPublicacion: Int=2000,
    var editorial: String="",
    var recetas: MutableList<Receta> = mutableListOf()
){
    override fun toString(): String {
        return "${titulo}"
    }
}
