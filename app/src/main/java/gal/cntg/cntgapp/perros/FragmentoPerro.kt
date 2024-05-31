package gal.cntg.cntgapp.perros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

// Tenemos que hacer que herede de Fragment
class FragmentoPerro: Fragment() {
    // Redefinimos métodos
    // en este método, preparamos la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}