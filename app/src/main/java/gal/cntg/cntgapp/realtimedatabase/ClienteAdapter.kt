package gal.cntg.cntgapp.realtimedatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import gal.cntg.cntgapp.R

class ClienteAdapter(val itemList: List<Cliente>, val borrarCliente: (Int)-> Unit) : Adapter<ClienteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }


    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = itemList[position]

        // Asignar los datos del cliente a los TextViews
        holder.itemView.findViewById<TextView>(R.id.nombreRecuperado).text = "Nombre: ${cliente.nombre}"
        holder.itemView.findViewById<TextView>(R.id.edadRecuperada).text = "Edad: ${cliente.edad}"
        holder.itemView.findViewById<TextView>(R.id.localidadRecuperada).text = "Localidad: ${cliente.localidad}"
        holder.itemView.findViewById<TextView>(R.id.nacionalidadRecuperada).text = "Nacionalidad: ${cliente.nacionalidad}"
        holder.itemView.findViewById<TextView>(R.id.emailRecuperado).text = "Email: ${cliente.email}"
        holder.itemView.findViewById<TextView>(R.id.claveRecuperada). text = "Clave: ${cliente.clave}"

        // Configurar el clic oyente para el botón de borrar
        holder.itemView.findViewById<Button>(R.id.btnBorrarCliente).setOnClickListener {
            // Llamar a la función para borrar cliente y pasar la posición del cliente
            borrarCliente(position)
        }

    }


    override fun getItemCount(): Int{
        return itemList.size
    }
}

