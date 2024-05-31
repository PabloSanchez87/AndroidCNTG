package gal.cntg.cntgapp.perros

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * En esta clase va a proporcionar los items del Carrusel a su ViewPager asociado
 * */
// FragmentStateAdapter gestiona el ciclo de vida del adapter
class AdapterFragmentosPerros (var fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){
    // TEnemos que sobreescribir estos métodos. // Cuantos tiene que tocar y pasarselos.
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}