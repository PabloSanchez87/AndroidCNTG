package gal.cntg.cntgapp.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Aquí vamos a comprobar el estado de conectividad del dispositivo.
 * Creamos un objeto pq sólo vamos a tener una instancia de este objeto en vez de una clase.
 * Productos puede haber varios. Objeto para comprobar internet llega con un sólo objeto.
 * */
object RedUtil {

    fun hayInternet(context: Context): Boolean{
        var hay = false

        // este servicio de android me permite acceder al estado de la red.
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // guardamos la red en uso
        val redEnUso = connectivityManager.activeNetwork

        // Guardamos en hay el valor según tenga una red o no.
        hay = (redEnUso!=null)

        return hay
    }
}