package com.example.b2023gr2sw

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(
                BEntrenador(1,"Santiago","s@s.com")
            )
            arregloBEntrenador.add(
                BEntrenador(2,"Mikaela","m@sm.com")
            )
            arregloBEntrenador.add(
                BEntrenador(3,"Carolina","c@c.com")
            )
        }
    }
}