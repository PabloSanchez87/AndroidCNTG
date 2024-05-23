package gal.cntg.cntgapp.imc

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
import gal.cntg.cntgapp.R


class ResultadoIMCActivity : AppCompatActivity() {

    // Se permite declarar sin inicializar en ese momento. "lateinit"
    lateinit var textoResultado: TextView
    lateinit var imagenResultado: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge()

        setContentView(R.layout.activity_resultado_imcactivity) // Carga xml del layout.

        // this es esa misma pantalla. la propia actividad
        this.textoResultado = findViewById<TextView>(R.id.textNombreImc)
        this.imagenResultado = findViewById<ImageView>(R.id.imageView)

        // Mostrar el resultado usando el intent desde IMCActivity.
        // intent es el precursor, el intenten que lanzó esta actividad
        val resultadoIMC = intent.getFloatExtra("IMC_RESULTADO", 0f)

        // Condiciones del IMC
        // Usamos un enumerado para seleccionar el texto a mostrar.
        when{
            resultadoIMC < 16 -> {                          // Usamos la clase Enum TipoImc.kt para pasar el texto.
                mostrarResultado(R.drawable.imc_desnutrido, TipoImc.DESNUTRIDO.toString())
            }
            resultadoIMC >= 16 && resultadoIMC < 18 -> {
                mostrarResultado(R.drawable.imc_delgado, TipoImc.DELGADO.toString())
            }
            resultadoIMC >= 18 && resultadoIMC < 25 -> {
                mostrarResultado(R.drawable.imc_ideal, TipoImc.IDEAL.toString())
            }
            resultadoIMC >= 25 && resultadoIMC < 31 -> {
                mostrarResultado(R.drawable.imc_sobrepeso, TipoImc.SOBREPESO.toString())
            }
            resultadoIMC >= 31 -> {
                mostrarResultado(R.drawable.imc_obeso, TipoImc.OBESO.toString())
            }
        }


        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }

    fun mostrarResultado(imagen:Int, texto:String):Unit
    {
        this.imagenResultado.setImageResource(imagen)
        this.textoResultado.text = texto
    }
}