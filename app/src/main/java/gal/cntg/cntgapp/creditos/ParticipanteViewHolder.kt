package gal.cntg.cntgapp.creditos

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.kotlinBasico.Participante

/**
*  Esta clase contiene a cada item o fila.
 *  Cada fila que está definida en fila_participante.xml se inflará
 *  Pasará de la definición textual xml a verse en memoria representada y en se visualiza.
 *  TERMINO IMPORTANTE EN ANDROID
 *  -->>>  "SE INFLARÁ" <<<--
 *  **********************************************
 *  Antes inflaría todos los participantes, pero ahora con el recycler en vez de representar "1000"
 *  inflará sólo los que se muestran. Si sólo se ven "10" a la vez se van reciclando según es necesario.
 *  Se reciclan, se va actualizando el contenido.
 *  **********************************************
 *  Esa cajita (las 10) son las que se reciclan, son el ViewHolder.
* */

//Recibimos el itemView que es la fila a rellenar
class ParticipanteViewHolder(itemView: View): ViewHolder(itemView) {

    //Vistas que tenemos en la fila del xml
    val nombre:TextView = itemView.findViewById<TextView>(R.id.nombreParticipante)
    val iconoGH:ImageView = itemView.findViewById<ImageView>(R.id.logoGH)
    val iconoIn:ImageView = itemView.findViewById<ImageView>(R.id.logoIN)

    fun rellenarViewHolderParticipante (participante: Participante){
        nombre.text = participante.nombre
        // tratar los enlaces TAG - Vista setTag

        //------------------------------------------
        // Set the URLs as tags
        iconoGH.tag = participante.urlGithub
        iconoIn.tag = participante.urlLinkedin

        /* Método Pablo
        // Set click listeners to open URLs
        iconoGH.setOnClickListener { //it sería la imagen que se ha tocado.(it - imagenTouch)
            val url = it.tag as String // Casteo a String pq uri.parse(url) me pide un string.
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)) // intent implícito
                it.context.startActivity(intent) // para lanzar el intent debemos tener el contexto.
            }
        }

        iconoIn.setOnClickListener {
            val url = it.tag as String
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                it.context.startActivity(intent)
            }
        }*/

        iconoGH.setOnClickListener(this::abrirWeb)
        iconoIn.setOnClickListener(this::abrirWeb)

    }

    fun abrirWeb(view: View): Unit {
        val url = view.tag as String
        if (url.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (intent.resolveActivity(this.itemView.context.packageManager)!=null) { // Controlamos que tenga con que abrirlo.
                view.context.startActivity(intent)
            }
        }else{
            // Toast enlace no disponible.
            Toast.makeText(view.context, "Enlace no disponible.", Toast.LENGTH_SHORT).show()
        }
    }

}