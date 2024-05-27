package gal.cntg.cntgapp.creditos

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.kotlinBasico.Participante

/**
* Esta clase es el Adapter del Recycler (Lista)
* A él, le dará los datos. Le irá dando las filas con los participantes que deben mostrarse.
* */
// Esta clase implementa la interfaz Adapter<ParticipanteViewHolder>()
class AdapterParticipantes (var listaParticipantes: List<Participante>) : Adapter<ParticipanteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipanteViewHolder {
        // Sirve para INFLAR/crear fila
        var participanteViewHolder: ParticipanteViewHolder

        // Tenemos que usar un servicio (servicios son importantes en ANDROID)
        // Profundizar en el concepto de CONTEXTO. Class Context
        var layoutInflater = LayoutInflater.from(parent.context)

        //Inflamos diciendo quien es su padre. false es para que no lo infle inmediatamente.
        var fila_participante = layoutInflater.inflate(R.layout.fila_participante, parent, false)
        // Se crea el Holder del contenedor de la fila.
        participanteViewHolder = ParticipanteViewHolder(fila_participante)

        return participanteViewHolder
    }

    override fun getItemCount(): Int {
        //DEvuelve el tamaño de la lista
        return this.listaParticipantes.size

    }

    override fun onBindViewHolder(holder: ParticipanteViewHolder, position: Int): Unit {
        // "CARGAR/RELLENAR" el viewHolder/Fila
        var participante = this.listaParticipantes.get(position)
        holder.rellenarViewHolderParticipante(participante)
    }


}