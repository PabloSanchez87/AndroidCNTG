

package gal.cntg.cntgapp.productos

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.util.RedUtil
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

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
    lateinit var slider: Slider
    lateinit var productosAdapter: ProductosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

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
                val adapter = ProductosAdapter(listadoProductos)
                recyclerView.adapter = adapter


                //it es una variable auxiliar, predefinida en Kotlin
                // para cuando recorro una colección (itero)
                // que va asumiuendo el valor de cada elemento de la colección
                //listadoProductos.forEach {
                //    Log.d("ForEach", "Producto ${it.toString()}")
                //}

                //TODO INICIAR SLIDER y FILTRAR LA LISTA CON EL PRECIO MEDIO CALCULADO
                productosAdapter = ProductosAdapter(listadoProductos)
                recyclerView.adapter = productosAdapter

                // Estamos colocando una barra de de progreso de carga, volviendo invisible al acabar.
                // Esta inicializada con un lateinit var al principio.
                this@ProductosActivity.progressBar = findViewById<ProgressBar>(R.id.barraProgreso)
                this@ProductosActivity.progressBar.visibility = View.INVISIBLE

                actualizarPostCarga()
            }
            //Log.d("CNTG APP", "onCreate")
        } else {
            Toast.makeText(this, "CNTG APP. Sin conexión a internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarPostCarga() {
        // Inicializamos el slider.
        this.slider = findViewById(R.id.sliderprecio)
        this.slider.visibility = View.VISIBLE

        // Obtenemos el producto más caro.
        var productoMasCaro = listadoProductos.maxBy { it.price.toFloat() } // hacemos un casting to float. Exite tmb un maxByOrNull

        // Obtenemos el producto más barato.
        var productoMasBarato = listadoProductos.minBy { it.price.toFloat() } // hacemos un casting to float. Exite tmb un minByOrNull

        // Obtenemos el valor del producto medio.
        var precioMedio = listadoProductos.map { it.price .toFloat() }.average() // it.price .toFloat() me devuelve una lista
                                                                                // y la lista tiene un método average. Devuelve un Double


        /**
         * Para añadir un scrollView y elegimos estos datos para tener datos para mostrar.
         *
        findViewById<TextView>(R.id.textViewPrecioMedio).text = "Precio medio: $precioMedio"
        findViewById<TextView>(R.id.textViewPrecioMasBarato).text = "Precio más barato: ${productoMasBarato.price}"
        findViewById<TextView>(R.id.textViewPrecioMasCaro).text = "Precio más caro: ${productoMasCaro.price}"
        *********************************************************************************/
        /* Otra opción pero usando String para que se traduzca con el string.xml*/
        //val precioMedioFormatted = getString(R.string.precio_medio, precioMedio.toString())
        //val precioMasBaratoFormatted = getString(R.string.precio_mas_barato, productoMasBarato.price.toString())
        //val precioMasCaroFormatted = getString(R.string.precio_mas_caro, productoMasCaro.price.toString())
        findViewById<TextView>(R.id.textViewPrecioMedio).text = getString(R.string.precio_medio, precioMedio.toString())
        findViewById<TextView>(R.id.textViewPrecioMasBarato).text =getString(R.string.precio_mas_barato, productoMasBarato.price.toString())
        findViewById<TextView>(R.id.textViewPrecioMasCaro).text = getString(R.string.precio_mas_caro, productoMasCaro.price.toString())

        slider.value = productoMasCaro.price.toFloat() // Valor por defecto donde aparece ubicado
        slider.valueFrom = productoMasBarato.price.toFloat()// valor mínimo del slider
        slider.valueTo = productoMasCaro.price.toFloat() // Valor máximo del slider

        // Este método dibuja la bandera. El valor donde se para el slider. Lo redondeamos para que sea más visible.
        slider.setLabelFormatter{
          "${it.roundToInt()} precio máx"
        }

        // para cuando cambie el slider de valor. Recibe 3 valores. El propio slider, el valor y si ha sido el usuario.
        // Estamos invocando la función abstracto void onValueChange por dentro.
        slider.addOnChangeListener{ slider, valor, isUser ->
            Log.d("CNTG APP", "Valor actual = $valor $isUser")
            var listadoProductosFiltrados = ListadoProductos() // lista auxiliar.
            listadoProductos.filter { producto -> producto.price.toFloat() <= valor }. toCollection(listadoProductosFiltrados)
                                                                                // recibe un predicado que responde si si o no.
                                                                                // nos devuelve una lista según el filtro.
            productosAdapter.itemList = listadoProductosFiltrados
            productosAdapter.notifyDataSetChanged() //los datos de la lista han cambiado, repíntate
        }

    }

    override fun onStart() {
        Log.d("CNTG APP", "en Start")
        super.onStart()
    }
}
