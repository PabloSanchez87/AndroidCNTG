package gal.cntg.cntgapp.productos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import gal.cntg.cntgapp.R

class ProductoViewHolder(itemView: View) : ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val nameView: TextView = itemView.findViewById(R.id.nameView)
    val priceView: TextView = itemView.findViewById(R.id.priceView)
    val idView: TextView = itemView.findViewById(R.id.idView)

    fun rellenarViewHolder(item: ListadoProductosItem) {
        nameView.text = item.name
        priceView.text = "Price: " + item.price
        idView.text = "ID: " + item.id.toString()

        // Cargar la imagen utilizando Glide desde la URL proporcionada en el JSON
        Glide.with(itemView.context)
            .load(item.imageUrl) // Aquí asumimos que item.imageUrl contiene la URL de la imagen
            .into(imageView)
    }
}
