

data class Libro(
    var titulo: String="",
    var autor: String="",
    var anioPublicacion: Int=2000,
    var editorial: String="",
    var recetas: MutableList<Receta> = arrayListOf(),

    ){
//    constructor():this("","",0,"", arrayListOf())
}
