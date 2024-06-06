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
        }.addOnFailureListener {
            Toast.makeText(this, "ERROR!! CLIENTE NO INSERTADO!!", Toast.LENGTH_LONG).show()
            Log.e("CNTG_APP", "ERROR INSERTANDO CLIENTE", it) // it sería el mensaje de la excepción.
        }
    }


}