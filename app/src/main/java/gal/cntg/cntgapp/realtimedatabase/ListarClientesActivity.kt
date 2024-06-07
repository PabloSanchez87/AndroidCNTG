package gal.cntg.cntgapp.realtimedatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import gal.cntg.cntgapp.databinding.ActivityListarClientesBinding

class ListarClientesActivity : AppCompatActivity() {

    lateinit var binding: ActivityListarClientesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener la lista de clientes del intent
        val listaClientes = intent.getSerializableExtra("listaClientes") as ArrayList<Cliente>

        // Configurar el RecyclerView
        binding.recyclerViewClientes.layoutManager = LinearLayoutManager(this)
        val adapter = ClienteAdapter(listaClientes)
        binding.recyclerViewClientes.adapter = adapter
    }
}
