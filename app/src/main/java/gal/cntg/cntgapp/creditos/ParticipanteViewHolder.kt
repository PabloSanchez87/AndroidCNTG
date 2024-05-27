package gal.cntg.cntgapp.creditos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
        // TODO tratar los enlaces TAG - Vista setTag
    }

}