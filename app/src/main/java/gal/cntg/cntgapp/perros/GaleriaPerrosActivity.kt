package gal.cntg.cntgapp.perros

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.util.RedUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GaleriaPerrosActivity : AppCompatActivity() {

    var raza: String = ""
    lateinit var textRazaPerro: TextView
    lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria_perros)
        raza = intent.getStringExtra("raza") ?: ""

        Log.d("CNTG APP", "Hay que buscar fotos de $raza")
        this.textRazaPerro = findViewById<TextView>(R.id.textRazaPerro)
        this.textRazaPerro.text = raza
        this.viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)

        // Tenemos que hacer la búsqueda. Generar la busqueda.

        if (RedUtil.hayInternet(this)) {
            var retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addConverterFactory(GsonConverterFactory.create())  // Estamos deserializando pasando texto a objetos.
                .build()

            var perroService: PerrosServices = retrofit.create(PerrosServices::class.java)

            /**
             * lifecycleScope: Buscar más información.
             * Evita perdida de recursos del móvil.
             * Cuando se abandona una actividad onStop, la tarea asyncrona asociada tmb es eliminada.
             * */
            lifecycleScope.launch {
                var listadoPerrosDescargado =
                    perroService.listarPerrosPorRaza(this@GaleriaPerrosActivity.raza)
                Log.d("CNTG APP", "Hemos recibido ${listadoPerrosDescargado.message.size} fotos")
                var adapterFragmentosPerros = AdapterFragmentosPerros(this@GaleriaPerrosActivity)
                adapterFragmentosPerros.listadoPerros = listadoPerrosDescargado
                viewPager2.adapter = adapterFragmentosPerros
            }
        } else {
            Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG).show()
            Log.w("CNTG APP", "SIN CONEXIÓN A INTERNET")
        }
    }
}

