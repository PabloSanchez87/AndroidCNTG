package gal.cntg.cntgapp.perros
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.util.RedUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GaleriaPerrosActivity : AppCompatActivity() {

    var raza:String = ""
    lateinit var textRazaPerro:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria_perros)
        raza = intent.getStringExtra("raza") ?: ""

        Log.d("CNTG APP", "Hay que buscar fotos de $raza")
        this.textRazaPerro = findViewById<TextView>(R.id.textRazaPerro)
        this.textRazaPerro.text = raza

        // Tenemos que hacer la búsqueda. Generar la busqueda.

        if (RedUtil.hayInternet(this)){
            var retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addConverterFactory(GsonConverterFactory.create())  // Estamos deserializando pasando texto a objetos.
                .build()

            var perroService:PerrosServices = retrofit.create(PerrosServices::class.java)

            /**
             * lifecycleScope: Buscar más información.
             * Evita perdida de recursos del móvil.
             * Cuando se abandona una actividad onStop, la tarea asyncrona asociada tmb es eliminada.
             * */
            lifecycleScope.launch {
                perroService.listarPerrosPorRaza(this@GaleriaPerrosActivity.raza)
            }
        }
    }
}

