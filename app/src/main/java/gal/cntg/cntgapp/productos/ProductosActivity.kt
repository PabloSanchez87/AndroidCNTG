

package gal.cntg.cntgapp.productos

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.util.RedUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author: Pablo Sánchez.
 * Esta clase, vamos a consumir el API Web
 * https://my-json-server.typicode.com/miseon920/json-api/products
 * y vamos a representar los productos
 * @see ListadoProductos
 * */
class ProductosActivity : AppCompatActivity() {

    lateinit var productoService: ProductoService       // este objeto instanciado es el que se encargara de traernos los datos.
                                                        // se encarga de hacerlo retrofit
    lateinit var listadoProductos: ListadoProductos
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)



        //Tengo que hacerlo fuera del if, sino no me reconoce el this.


        // Crear el productoService, que es el objeto encargado de traerse los datos como nos indica Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")//aquí estamos poniendo la base de la url, lo que falta en el get del interface
                                                            // Hemos añadido otra libreria de terceros com.squareup.retrofit2 el paquete que se llama "converter-gson"
                                                            // para poder usar el GsonConverterFactory.create()
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        productoService = retrofit.create(ProductoService::class.java)

        if (RedUtil.hayInternet(this)) {
            // Cargamos los productos
            // Tenemos que definir una corutina (pequeño programa paralelo que se encarga de la comunicación HTTP.
            lifecycleScope.launch {
                // aquí pedimos los datos, pero mientras nuestro programa sigue. Por eso estamos usando el lifecycleScope
                //Log.d("CNTG APP", "Pidiendo datos")
                listadoProductos = productoService.obtenerProductos()
                //Log.d("CNTG APP", "Hemos recibido ${listadoProductos.size} productos")

                this@ProductosActivity.recyclerView = findViewById(R.id.recyclerViewProductos)
                recyclerView.layoutManager = LinearLayoutManager(this@ProductosActivity)
                val adapter = ProductoAdapter(listadoProductos)
                recyclerView.adapter = adapter


                //it es una variable auxiliar, predefinida en Kotlin
                // para cuando recorro una colección (itero)
                // que va asumiuendo el valor de cada elemento de la colección
                //listadoProductos.forEach {
                //    Log.d("ForEach", "Producto ${it.toString()}")
                //}

                // Estamos colocando una barra de de progreso de carga, volviendo invisible al acabar.
                // Esta inicializada con un lateinit var al principio.
                this@ProductosActivity.progressBar = findViewById<ProgressBar>(R.id.barraProgreso)
                this@ProductosActivity.progressBar.visibility = View.INVISIBLE
            }
            //Log.d("CNTG APP", "onCreate")
        } else {
            Toast.makeText(this, "CNTG APP. Sin conexión a internet", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onStart() {
        Log.d("CNTG APP", "en Start")
        super.onStart()
    }
}
