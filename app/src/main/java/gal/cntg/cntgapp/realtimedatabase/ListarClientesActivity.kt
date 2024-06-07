package gal.cntg.cntgapp.realtimedatabase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gal.cntg.cntgapp.databinding.ActivityListarClientesBinding

class ListarClientesActivity : AppCompatActivity() {

    lateinit var binding: ActivityListarClientesBinding
    lateinit var listaClientes: ArrayList<Cliente>
    lateinit var databaseReference: DatabaseReference
    lateinit var recyclerViewClientes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener la lista de clientes del intent
        listaClientes = intent.getSerializableExtra("listaClientes") as ArrayList<Cliente>

        // Configurar el RecyclerView
        binding.recyclerViewClientes.layoutManager = LinearLayoutManager(this)
        val adapter = ClienteAdapter(listaClientes) { position -> borrarCliente(position) }
        binding.recyclerViewClientes.adapter = adapter

        // Inicializamos la referencia de la base de datos
        databaseReference = FirebaseDatabase.getInstance(URL_REAL_TIME_DATABASE_CLIENTES_FIREBASE).reference
    }


    // Función para borrar un cliente
    fun borrarCliente(position: Int) {
        // Verificar si la lista no está vacía y si la posición es válida
        Log.d("CNTG_APP_REMOVE", "posicion: $position y ${listaClientes.isNotEmpty()} y ${listaClientes.size-1}")
        if (listaClientes.isNotEmpty() && position >= 0 && position < listaClientes.size) {
            Log.d("CNTG_APP_REMOVE", "posicion: $position y ${listaClientes.isNotEmpty()} y ${listaClientes.size-1}")
            // Obtener el cliente que se va a borrar
            val clienteBorrar = listaClientes[position]

            // Eliminar el cliente de Firebase Realtime Database
            databaseReference.child("Clientes").child(clienteBorrar.clave).removeValue()
                .addOnCompleteListener { tarea ->
                    if (tarea.isSuccessful) {
                        // Notificar al adaptador después de eliminar en la base de datos
                        binding.recyclerViewClientes.adapter!!.notifyItemRangeChanged(position, listaClientes.size)

                        // Eliminar el elemento de la lista
                        listaClientes.removeAt(position)

                        // Verificar si la lista está vacía después de eliminar el elemento
                        if (listaClientes.isEmpty()) {
                            // Terminar la actividad
                            finish()

                            // Mostrar un aviso por pantalla (opcional)
                            Toast.makeText(this, "La lista de clientes está vacía", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        // Si falla la eliminación, mostrar un mensaje de error
                        Toast.makeText(this, "Error al borrar cliente", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            // Mostrar un mensaje si la lista está vacía o la posición no es válida
            Toast.makeText(this, "La lista está vacía o la posición no es válida", Toast.LENGTH_LONG).show()
        }
    }
}

