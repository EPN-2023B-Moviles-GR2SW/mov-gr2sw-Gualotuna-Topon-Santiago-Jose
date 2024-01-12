


    abstract class Numeros(
        //Ejemplo:
        //uno: Int, (Parametro ( Sin modificador de acceso))
        //private var uno: Int, //Propiedad Publica Clase numeros.uno
        //var uno: Int, //Propiedad de la clase (PUBLIC por defecto)
        //public var uno:Int;
        protected val numeroUno: Int, // Propiedad de la clase protected numeros.numeroUno
        protected val numeroDos: Int, // Propiedad de la clase protected numeros.numeroDos
    ) {
        // var cedula: String = ""; (public es por defecto)
        // private valorCalculado : Int =0; (private)

        init { // bloque constructor primario
            this.numeroUno; this.numeroDos; // this es opcional
            numeroUno;numeroDos; // sin el "this", es lo mismo
            println("Inicializando");
        }

    }

    class Suma(
        // Constructor Primario Suma
        unoParametro: Int, // Parametro
        dosParametro: Int, // Parametro
    ) : Numeros(unoParametro, dosParametro) { // Extendiendo y Mandado Los Parametros

        init { // Bloque Codigo Constructor Primario
            this.numeroDos;
            this.numeroDos;
        }

        constructor( // Segundo constructor
            uno: Int?, // Parametros
            dos: Int //Parametros
        ) : this(
            if (uno == null) 0 else uno,
            dos
        )

        constructor( // Tercer Constructor)
            uno: Int, // Parametros
            dos: Int? // Parametros
        ) : this(
            uno,
            if (dos == null) 0 else dos,
        )

        constructor( // Cuarto constructor
            uno: Int?,
            dos: Int?
        ) : this(
            if (uno == null) 0 else uno,
            if (dos == null) 0 else dos
        )

        public fun sumar(): Int {
            val total = numeroUno + numeroDos;
            //Suma.agregarHistorial
            agregarHistorial(total);
            return total;
        }
        companion object { // Atributos y metodos compartidos entre las instancias
            val pi = 3.14;
            fun elevarAlCuadrado(num: Int): Int {
                return num * num;
            }
            val historialSumas = arrayListOf<Int>();
            fun agregarHistorial(valorNuevaSuma:Int){
                historialSumas.add(valorNuevaSuma);
            }

        }
    }

//
//    val sumaUno = Suma(1,1);
//    val sumaDos = Suma(null, 1);
//    val sumaTres = Suma(1, null);
//
//
//    val sumaCuatro = Suma(null, null);
