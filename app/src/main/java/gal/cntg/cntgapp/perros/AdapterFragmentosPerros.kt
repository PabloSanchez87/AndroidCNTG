package gal.cntg.cntgapp.perros

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * En esta clase va a proporcionar los items del Carrusel a su ViewPager asociado
 * */
// FragmentStateAdapter gestiona el ciclo de vida del adapter
class AdapterFragmentosPerros (var fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){

    var listadoPerros:ListadoPerros ?= null


    // TEnemos que sobreescribir estos métodos. // Cuantos tiene que tocar y pasarselos.
    override fun getItemCount(): Int {
        return listadoPerros!!.message.size // !! con esto kotlin no checkea la posibilidad que sea null.
        // rerurn litadoPerros?.message?.size ?: 0 --> tmb valdría.
    }

    override fun createFragment(position: Int): Fragment {
        var fragmentoPerro: Fragment ?= null // representa el item del carrusel.

        fragmentoPerro = FragmentoPerro() // --> Rellenamos el fragment.

        var bundle = Bundle() // creamos un saco temporal donde puedo guardar lo datos.

        var urlFoto = listadoPerros?.message?.get(position)
        var posicion = position + 1 // para mostras la posición de las fotos "x de X"
        var textoLeyendaContador = "$posicion de ${listadoPerros?.message?.size}"

        //pasamos al bundle los datos para rellenarlo en la otra clase.
        bundle.putString("url_foto", urlFoto)
        bundle.putString("texto_leyenda", textoLeyendaContador)

        fragmentoPerro.arguments = bundle //le pasamos el "saco/bundle" para tener acceso desde FragmentoPerros.kt


        return fragmentoPerro
    }
}