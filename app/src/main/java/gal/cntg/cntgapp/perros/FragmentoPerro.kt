package gal.cntg.cntgapp.perros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import gal.cntg.cntgapp.R

// Tenemos que hacer que herede de Fragment
class FragmentoPerro : Fragment() {
    // Redefinimos métodos
    // en este método, preparamos la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, // todas las vistas quedan anidadas en container.
        savedInstanceState: Bundle?
    ): View? {

        // En este método "de rellena el Fragment." ## arguments es un "atributo especial"

        // Tenemos que inflar el fragmentoPerro.
        //      "container" sería el padre.
        //      "false" para que no se actualice en el momento, que podamos manipularlo.
        var itemCarrusel: View? = inflater.inflate(R.layout.fragment_perro, container, false)

        val urlFoto = arguments?.getString("url_foto")
        val textoLeyenda = arguments?.getString("texto_leyenda")

        var imageViewPerro = itemCarrusel?.findViewById<ImageView>(R.id.imageViewPerro)
        var textViewLeyenda = itemCarrusel?.findViewById<TextView>(R.id.textoLeyendaPerro)

        textViewLeyenda?.text = textoLeyenda // Introducimos el texto
        Picasso.get()
            .load(urlFoto)
            .into(imageViewPerro) // Introducimos con Picaso la foto

        // Return por defecto --> return super.onCreateView(inflater, container, savedInstanceState)
        return itemCarrusel
    }
}