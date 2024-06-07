package gal.cntg.cntgapp.realtimedatabase

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import gal.cntg.cntgapp.R

class ClienteViewHolder(itemView: View) : ViewHolder(itemView) {
    val nombreRecuperado: TextView = itemView.findViewById(R.id.nombreRecuperado)
    val edadRecuperada: TextView = itemView.findViewById(R.id.edadRecuperada)
    val localidadRecuperada: TextView = itemView.findViewById(R.id.localidadRecuperada)
    val nacionalidadRecuperada: TextView = itemView.findViewById(R.id.nacionalidadRecuperada)
    val emailRecuperado: TextView = itemView.findViewById(R.id.emailRecuperado)

    fun bind(cliente: Cliente) {
        nombreRecuperado.text = cliente.nombre
        edadRecuperada.text = cliente.edad.toString()
        localidadRecuperada.text = cliente.localidad
        nacionalidadRecuperada.text = cliente.nacionalidad
        emailRecuperado.text = cliente.email
    }
}
