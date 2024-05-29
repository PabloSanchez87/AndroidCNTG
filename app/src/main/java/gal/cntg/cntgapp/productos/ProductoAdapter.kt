package gal.cntg.cntgapp.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import gal.cntg.cntgapp.R

class ProductoAdapter(val itemList: List<ListadoProductosItem>) : Adapter<ProductoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    //Preguntar.
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        //var item = this.itemList.get(position)
        //holder.rellenarViewHolder(item)

        holder.rellenarViewHolder(itemList[position])
    }
}
