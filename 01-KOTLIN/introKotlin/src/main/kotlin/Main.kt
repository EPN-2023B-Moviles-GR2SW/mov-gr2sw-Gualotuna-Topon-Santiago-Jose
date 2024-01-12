import java.util.Date


fun main(args: Array<String>) {
    println("Hello World!")
    // VARIABLES INMUTABLES (NO se reasignar; se conocen como constantes )
    val inmutable: String = "Adrian";
    //  inmutable ="Vicente";

    //VARIABLES MUTABLES (Se pueden reasiganar)
    var mutable:String="Vicente";
    mutable="Adrian";

    //val > var
    //DUCK TYPING
    var ejemploVariable = " Santiago ";  // reconoce el tipo de dato, en este caso String
    var edadEjemplo:Int =12;
    ejemploVariable.trim();
    // ejemploVariable = edadEjemplo; X no se pude hacer asignar un Int a un String

    // VARIABLE PRIMITIVA
    val nombreProfesor: String = "Adrian Eguez";
    val sueldo:Double=1.3;
    val estadoCivil:Char = 'C';
    val mayorEdad: Boolean = true;
    //CLASES JAVA
    val fechaNacimiento: Date = Date();


    //CONDICIONALES
    //1- Switch
    val estadoCivilWhen:Char = 'C';
    when (estadoCivilWhen){
        ('C')->{
            println("Casado");
        }
        ('S')->{
            println("Soltero");
        }
        else -> {
            println("No sabemos");
        }
    }
    val esSoltero:Boolean =(estadoCivil == 'C');
    val coqueto:String = if (esSoltero) "Si" else "No";


    //FUNCIONES
    //void -> Unit
    fun imprimitNombre(nombre:String):Unit{
        println("Nombre: ${nombre}");
    }

    fun calcularSueldo(
        sueldo:Double, // parametor requerido
        tasa:Double = 12.0, // parametro Opcional(por defecto)
        bonoEspecial:Double?=null //Puede ser Double o null -> nullable
    ):Double{
        //Int -> Int? (nullable)
        //String -> String? (nullable)
        //Date -> Date? (nullable)
        if(bonoEspecial ==null){
            return sueldo * (100/tasa);
        }else{
            return sueldo * (100/tasa) + bonoEspecial;
        }

    }

    calcularSueldo(10.00);
    calcularSueldo(10.00,15.00,20.00);
    calcularSueldo(10.00, bonoEspecial = 20.00); //Named Parameters
    calcularSueldo(bonoEspecial = 20.00,sueldo=10.00, tasa = 14.00); // Parametros



    //CLASES

    //CLASE ABASTRACTA

    abstract class NumerosJava{
        protected val numeroUno: Int;
        protected val numeroDos: Int;

        constructor(uno:Int,dos:Int){
            this.numeroUno = uno;
            this.numeroDos = dos;
            println("Inicializando");
        }
    }

    abstract class Numeros(
        //Ejemplo:
        //uno: Int, (Parametro ( Sin modificador de acceso))
        //private var uno: Int, //Propiedad Publica Clase numeros.uno
        //var uno: Int, //Propiedad de la clase (PUBLIC por defecto)
        //public var uno:Int;
        protected val numeroUno: Int, // Propiedad de la clase protected numeros.numeroUno
        protected val numeroDos: Int, // Propiedad de la clase protected numeros.numeroDos
    ){
        // var cedula: String = ""; (public es por defecto)
        // private valorCalculado : Int =0; (private)

        init { // bloque constructor primario
            this.numeroUno; this.numeroDos; // this es opcional
            numeroUno;numeroDos; // sin el "this", es lo mismo
            println("Inicializando");
        }

    }

//    class Suma( // Constructor Primario Suma
//        unoParametro: Int, // Parametro
//        dosParametro: Int, // Parametro
//    ): Numeros(unoParametro,dosParametro){ // Extendiendo y mandando lso parametros (super)
//        init { //bloque de codigo contructor primario
//            this.numeroUno;
//            this.numeroDos
//        }
//    }

//
//    class Suma( // Constructor Primario Suma
//        unoParametro: Int, // Parametro
//        dosParametro: Int, // Parametro
//    ): Numeros (unoParametro, dosParametro) { // Extendiendo y Mandado Los Parametros
//
//        init { // Bloque Codigo Constructor Primario
//            this.numeroDos;
//            this.numeroDos;
//        }
//        constructor( // Segundo constructor
//            uno: Int?, // Parametros
//            dos: Int //Parametros
//        ) : this(
//            if (uno == null) 0 else uno,
//            dos
//        )
//
//        constructor( // Tercer Constructor)
//            uno: Int, // Parametros
//            dos: Int? // Parametros
//        ) : this(
//            uno,
//            if (dos == null) 0 else dos,
//        )
//
//        constructor( // Cuarto constructor
//            uno:Int?,
//            dos:Int?
//        ):this(
//            if(uno==null)0 else uno,
//            if(dos==null)0 else dos
//        )
//
//        public fun sumar():Int{
//            val total=numeroUno + numeroDos;
//            //Suma.agregarHistorial
//            agregarHistorial(total);
//            return total;
//        }
//        companion object{ // Atributos y metodos compartidos entre las instancias
//            val pi = 3.14;
//
//            fun elevarAlCuadrado(num:Int):Int{
//                return num*num;
//            }
//            val historialSumas = arrayListOf<Int>();
//
//            fun agregarHitorial(valorNuevaSuma:Int){
//                historialSumas.add(valorNuevaSuma);
//            }
//
//        }
//
//    }

//    val


//    val sumaUno = Suma(1,1);
//    val sumaDos = Suma(null, 1);
//    val sumaTres = Suma(1, null);


//    val sumaCuatro = Suma(null, null);



    val sumaUno = Suma(1,1);
    val sumaDos = Suma(null, 1);
    val sumaTres = Suma(1, null);


    val sumaCuatro = Suma(null, null);


    sumaUno.sumar();
    sumaDos.sumar();
    sumaTres.sumar();
    sumaCuatro.sumar();

    println(Suma.pi);
    println(Suma.elevarAlCuadrado(2));
    println(Suma.historialSumas);


    // ARREGLOS ESTATICOS

    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3);
    println(arregloEstatico);


    //ARREGLOS DINAMICOS
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10);
    println(arregloDinamico);
    arregloDinamico.add(11);
    arregloDinamico.add(12);
    println(arregloDinamico);


    //FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach{valorActual:Int->
        println("Valor actual: ${valorActual}");
    };

    //it (en ingles eso) significa el elemento iterado

    arregloDinamico.forEach{ println("Valor actual: ${it}") };
    arregloEstatico.forEachIndexed { indice:Int, valorActual:Int ->
        println("Valor ${valorActual}, Indice: ${indice}")
    }

    //Or / And
    // Or --> Any (alguno cumple?)
    //And --> all(todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico.any{valorActual:Int->
        return@any(valorActual>5)
    }
    println(respuestaAny); // true

    val respuestaAll: Boolean = arregloDinamico.all { valorActual:Int ->
        return@all(valorActual>5)
    }
    println(respuestaAll); //false


    //Reduce  --> valor acumulado
    // Valor acumulado = 0 (Siempre 0  en lenguaje kotlin)
    //[1,2,3,4,5] --> suma todos los valores del arreglo
    // valorIteracion1 = valorEmpieza +1 = 0+1 = 1 -->Iteracion #1
    // ValorIteracion2 = valorIteracion1 + 2 = 1+2 = 3 --> Iteracion #2
    // valorIteracion3 = valorIteracion2 + 3 = 3+3 = 6 --> Iteracion #3
    // valorIteracion4 = valoriteracion3 + 4 = 6+4 = 10 --> Iteracion #4
    // valorIteracion5 = valoriteracion4 + 5 = 10+5 = 15 --> Iteracion #5

    val respuestaReduce: Int = arregloDinamico.reduce{
        acumulado: Int, valorActual:Int ->
        return@reduce(acumulado+valorActual) // Logica de negocio
    }
    println(respuestaReduce);















}