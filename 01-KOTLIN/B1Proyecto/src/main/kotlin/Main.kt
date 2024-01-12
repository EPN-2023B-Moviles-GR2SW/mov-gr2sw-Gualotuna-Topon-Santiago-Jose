import java.util.Scanner

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`);
    val manager = LibroManager();
    val listlibros = manager.leerJsonDesdeArchivoV2();
    if(!listlibros.isEmpty()){
        manager.setLibros(listlibros);
    }
    try {
        do {
            println("**********LIBROS DE RECETAS**********");
            println("Ingrese la opcion deseada");
            println("1- Agregar Libro");
            println("2- Agregar recetas");
            println("3- Mostrar libros");
            println("4- Mostrar libro por Titulo");
            println("5- Eliminar Libro");
            println("6- Actualizar libro");
            println("7- Eliminar Receta");
            println("0- Salir \n");
            print("Ingresa una opcion: ");
            val opcion = sc.nextInt();

            when(opcion){
                1 -> {
                    print("Ingrese el título del libro: ")
                    val titulo = sc.next();

                    print("Ingrese el autor del libro: ")
                    val autor = sc.next();

                    print("Ingrese al editorial del libro: ")
                    val editorial = sc.next();

                    print("Ingrese el anio de publicacion: ")
                    val anioPublicacion = sc.nextInt();

                    val libro = Libro(titulo,autor,anioPublicacion,editorial);
                    manager.agregarLibro(libro)
                    println(" ");
                }
                2->{
                    manager.obtenerLibros();
                    print("Ingrese el id del libro: ");
                    val idLibro = sc.nextInt();

                    print("Ingrese el nombre de la receta: ");
                    val nombre: String = sc.next();

                    print("Ingrese la nacionalidad de la receta: ");
                    val nacionalidad: String = sc.next();

                    print("Ingrese el tiempo de preparacion: ");
                    val tiempoPreparacion:Int = sc.nextInt();
                    sc.nextLine()

                    print("Ingrese los ingredientes de la receta (separados por comas): ")
                    val ingredientes = sc.nextLine().split(",").map { it.trim() };

                    print("Ingrese los pasos de la receta (separados por comas): ")
                    val pasos = sc.nextLine().split(",").map { it.trim() };

                    println("agregando........");

                    val receta = Receta(nombre,nacionalidad,tiempoPreparacion,ingredientes.toMutableList(),pasos.toMutableList());
                    manager.agregarReceta(idLibro,receta);
                    println(" ");
                }
                3->{
                    manager.obtenerLibros();
                }
                4->{
                    sc.nextLine();
                    print("Ingrese el nombre del libro a buscar: ");
                    val titulo = sc.nextLine();

                    val libro = manager.obtenerLibroPorTitulo(titulo);
                    println(libro);
                }
                5->{
                    print("Ingrese el id del libro que desea eliminar: ");
                    val idLibro = sc.nextInt();
                    manager.eliminarLibro(idLibro);
                }
                6->{
                    manager.obtenerLibros();
                    print("Ingrese el id del libro: ");
                    val idLibro = sc.nextInt();
                    sc.nextLine();

                    print("Ingrese el título nuevo del libro: ")
                    val titulo = sc.next();

                    print("Ingrese el autor nuevo del libro: ")
                    val autor = sc.next();

                    print("Ingrese al editorial nueva del libro: ")
                    val editorial = sc.next();

                    print("Ingrese el anio nuevo de publicacion: ")
                    val anioPublicacion = sc.nextInt();

                    val libroActualizado = Libro(titulo,autor,anioPublicacion,editorial);
                    manager.actualizarlibro(idLibro,libroActualizado);


                }
                7->{
                    manager.obtenerLibros();
                    print("Ingrese el id del libro: ");
                    val idLibro = sc.nextInt();
                    sc.nextLine();

                    val libro = manager.buscarLibroPorId(idLibro);
                    if(libro !==null){
                        manager.mostrarRecetas(libro);
                        print("Ingrese el id de la receta: ");
                        val idReceta = sc.nextInt();
                        manager.eliminarReceta(idLibro,idReceta);
                    }else{
                        println("libro no encontrado con el id: ${idLibro}");
                    }

                }
            }


        }while (opcion !==0);
    }catch (e: Exception){
        println("Error al ingresar la opcion: ${e.message}");
    }


    manager.guardarJsonEnArchivov2(manager.getLibros())




}