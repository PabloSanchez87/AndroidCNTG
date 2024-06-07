package gal.cntg.cntgapp.realtimedatabase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gal.cntg.cntgapp.databinding.ActivityInsertarClientesBinding
import gal.cntg.cntgapp.util.RedUtil


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

    /*fun crearCliente(view: View) {
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
    }*/

    fun crearCliente(view: View) {
        // Verificar si hay conexión a Internet utilizando RedUtil.hayInternet()
        if (RedUtil.hayInternet(this)) {
            // Si hay conexión a Internet, proceder con la creación del cliente
            // Creamos el objeto cliente
            // Habría que hacer un trabajo de validación. Estamos suponiendo que está correcto.
            val cliente = Cliente(
                binding.editTextEdadCliente.text.toString().toLong(),
                binding.editTextLocalidad.text.toString(),
                binding.editTextNombreCliente.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextNacionalidad.text.toString()
            )

            // Generar un ID único para el cliente en la base de datos
            val idClaveCliente = this.databaseReference.push().key
            cliente.clave = idClaveCliente!!

            // Insertar el cliente en la base de datos
            this.databaseReference.child("Clientes").child(idClaveCliente).setValue(cliente)
                .addOnCompleteListener { tarea ->
                    // Mostrar un mensaje de éxito cuando se complete la operación
                    Toast.makeText(this, "CLIENTE INSERTADO EN BBDD", Toast.LENGTH_LONG).show()
                    // Finalizar la actividad y volver a iniciarla para actualizar los datos
                    finish()
                    startActivity(this.intent)
                }.addOnFailureListener { excepcion ->
                    // Mostrar un mensaje de error si falla la inserción del cliente
                    Toast.makeText(this, "ERROR!! CLIENTE NO INSERTADO!!", Toast.LENGTH_LONG).show()
                    Log.e("CNTG_APP", "ERROR INSERTANDO CLIENTE", excepcion)
                }
        } else {
            // Si no hay conexión a Internet, mostrar un mensaje de error al usuario
            Toast.makeText(this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show()
        }
    }



    fun listarClientes(view: View) {
        // Verificar si hay conexión a Internet utilizando RedUtil.hayInternet()
        if (RedUtil.hayInternet(this)) {
            // Si hay conexión a Internet, obtener los datos de Firebase
            this.databaseReference.child("Clientes").get().addOnSuccessListener { datos ->
                // Verificar si los datos no son nulos y contienen algún valor
                if (datos != null && datos.exists()) {
                    // Convertir los datos obtenidos en una lista de clientes
                    val lista = datos.value as? Map<String, Map<String, Any>>
                    if (lista != null) {
                        val listaClientes = ArrayList<Cliente>()

                        lista.forEach { (claveId, valor) ->
                            // Crear un objeto Cliente para cada entrada en los datos
                            val cliente = Cliente(
                                valor["edad"] as Long,
                                valor["localidad"].toString(),
                                valor["nombre"].toString(),
                                valor["email"].toString(),
                                valor["nacionalidad"].toString(),
                                claveId
                            )
                            listaClientes.add(cliente)
                        }

                        // Iniciar la nueva actividad y pasar los datos de los clientes como extra en el intent
                        val intent = Intent(this, ListarClientesActivity::class.java)
                        intent.putExtra("listaClientes", listaClientes)
                        startActivity(intent)
                    } else {
                        // Mostrar un mensaje indicando que los datos no son válidos
                        Toast.makeText(this, "Los datos obtenidos no son válidos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Mostrar un mensaje indicando que no se encontraron datos en Firebase
                    Toast.makeText(this, "No se encontraron datos en Firebase", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { exception ->
                // Mostrar un mensaje de error si falla la obtención de datos de Firebase
                Toast.makeText(this, "Error al obtener datos de Firebase: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Si no hay conexión a Internet, mostrar un mensaje de error al usuario
            Toast.makeText(this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show()
        }
    }


    // TODO --> El código de abajo es lo que programos en clase.
    /*
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

    }*/


}