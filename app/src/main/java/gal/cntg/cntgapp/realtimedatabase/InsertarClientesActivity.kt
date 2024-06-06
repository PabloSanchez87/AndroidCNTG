package gal.cntg.cntgapp.realtimedatabase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gal.cntg.cntgapp.databinding.ActivityInsertarClientesBinding

const val URL_REAL_TIME_DATABASE_CLIENTES_FIREBASE = "https://cntg-app-server-default-rtdb.europe-west1.firebasedatabase.app/"
class InsertarClientesActivity : AppCompatActivity() {

    lateinit var binding: ActivityInsertarClientesBinding
    lateinit var databaseReference: DatabaseReference // es la conexcion a la BBDD.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // preparamos el binding. cambiamos el main a mainic en el xml de la activity a inflar.
        binding = ActivityInsertarClientesBinding.inflate(layoutInflater)
        setContentView(binding.mainic)

        // Acceso a la BBDD.
        this.databaseReference = FirebaseDatabase.getInstance(
            URL_REAL_TIME_DATABASE_CLIENTES_FIREBASE).reference

    }

    fun crearCliente(view: View) {
        // Creamos el objeto cliente
        // Habría que hacer un trabajo tiempo de validación. // Estamos suponiendo que está correcto.
        // Cliente(val edad:Long, val localidad:String, val nombre:String, val email:String, val nacionalidad:String, var clave:String="")
        val cliente = Cliente(binding.editTextEdadCliente.text.toString().toLong(),
            binding.editTextLocalidad.text.toString(), binding.editTextNombreCliente.text.toString(),
            binding.editTextEmail.text.toString(), binding.editTextNacionalidad.text.toString())
                    // la clave es opcional, se la daremos más adelante pq no la sabemos.

        val idClaveCliente = this.databaseReference.push().key // generamos un id desde la BBDD.
        cliente.clave = idClaveCliente!!

        // así insertamos el cliente, con la ruta de la BBDD no relacional. Clientes/idclaveCliente/Cliente
        // le indicamos que el id no será null con !!
        this.databaseReference.child("Clientes").child(idClaveCliente).setValue(cliente).addOnCompleteListener {
            tarea -> Toast.makeText(this, "CLIENTE INSERTADO EN BBDD", Toast.LENGTH_LONG).show()
            finish()
            startActivity(this.intent)
        }.addOnFailureListener { // Controlamos si falla el insertado.
            Toast.makeText(this, "ERROR!! CLIENTE NO INSERTADO!!", Toast.LENGTH_LONG).show()
            Log.e("CNTG_APP", "ERROR INSERTANDO CLIENTE", it) // it sería el mensaje de la excepción.
        }
    }

    fun listarClientes(view: View) {

        Log.d("CNTG_APP", "Mostrando clientes de la BD")
        this.databaseReference.child("Clientes").get().addOnSuccessListener {
                datos ->
            var claveDocumento = datos.key
            Log.d("CNTG_APP", "Clave documento = $claveDocumento")
            var lista = datos.value as Map<String, Map<String, Any>> //los registros
            var entradas = lista.entries
            var nclis = entradas.size
            var listaClientes = ArrayList<Cliente>()

            entradas.forEach { (claveId, valor) ->
                Log.d("CNTG_APP", "id cliente = $claveId")
                Log.d("CNTG_APP", "nombre = ${valor.get("nombre")}")
                Log.d("CNTG_APP", "email = ${valor.get("email")}")
                Log.d("CNTG_APP", "localidad = ${valor.get("localidad")}")
                Log.d("CNTG_APP", "edad = ${valor.get("edad")}")
                Log.d("CNTG_APP", "nacionalidad = ${valor.get("nacionalidad")}")
                //(val edad:Int, val localidad:String, val nombre: String, val email:String, val nacionalidad:String)
                var cliente = Cliente (valor.get("edad") as Long, valor.get("localidad").toString(),
                    valor.get("nombre").toString(), valor.get("email").toString(), valor.get("nacionalidad").toString(), claveId)

                listaClientes.add(cliente)
                if (listaClientes.size==nclis)
                {
                    mostrarClientes(listaClientes)
                }

            }
        }
    }

    private fun mostrarClientes(listaClientes: ArrayList<Cliente>) {

        listaClientes.forEach{Log.d("CNTG_APP", "${it}")}

        //TODO lo suyo, sería hacer un RECYCLER
        //borrar registro por clave
        /**
         *  var registro = bdref.child("/clientes").child(id)
         *   registro.removeValue().addOnCompleteListener { tarea -> }
         */

    }


}