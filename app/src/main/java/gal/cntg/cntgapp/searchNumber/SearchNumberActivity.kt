package gal.cntg.cntgapp.searchNumber

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.util.Constantes
import kotlin.random.Random



class SearchNumberActivity : AppCompatActivity() {
    private var numeroAleatorio : Int = 0
    private var intentos: Int = Constantes.MAX_INTENTOS

    private lateinit var numeroIntroducido: EditText
    private lateinit var checkBoton: Button
    private lateinit var resultadoTextView: TextView
    private lateinit var reiniciarBoton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_number)

        inicializarVistas()
        configurarJuego()

    }

    private fun inicializarVistas() {
        numeroIntroducido = findViewById(R.id.numberIn)
        checkBoton = findViewById(R.id.boton)
        resultadoTextView = findViewById(R.id.resultado)
        reiniciarBoton = findViewById(R.id.reiniciar)

    }

    private fun configurarJuego() {
        numeroAleatorio  = generarNumeroAleatorio()
        reiniciarBoton.visibility = View.INVISIBLE


        checkBoton.setOnClickListener { comprobarNumero() }
        reiniciarBoton.setOnClickListener { reiniciarJuego() }
    }

    private fun comprobarNumero(){
        val userInput = numeroIntroducido.text.toString()
        if (userInput.isNotEmpty()) {
            val userNumber = userInput.toInt()
            intentos--
            val result: String
            if (userNumber == numeroAleatorio ) {
                result = "Correcto! Has acertado! El número era $numeroAleatorio "
                checkBoton.isEnabled = false
                reiniciarBoton.visibility = View.VISIBLE
            } else {
                result = if (intentos > 0) {
                    if (userNumber > numeroAleatorio ) {
                        "Has fallado! Vuelva a intentarlo. El número es menor. Intentos restantes: $intentos."
                    } else {
                        "Has fallado! Vuelva a intentarlo. El número es mayor. Intentos restantes: $intentos."
                    }
                } else {
                    "Game Over!! El número correcto era  $numeroAleatorio . Te has quedado sin intentos."
                }
                if (intentos == 0) {
                    // Mostrar el botón de reinicio cuando se agoten los intentos
                    reiniciarBoton.visibility = View.VISIBLE
                    checkBoton.visibility = View.INVISIBLE
                }
            }
            resultadoTextView.text = result

            numeroIntroducido.text.clear() // Vaciamos la caja del editText
        } else {
            Toast.makeText(this, "Introduce un número", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reiniciarJuego() {
        numeroAleatorio = generarNumeroAleatorio()
        intentos = Constantes.MAX_INTENTOS
        checkBoton.isEnabled = true
        reiniciarBoton.visibility = View.INVISIBLE
        checkBoton.visibility = View.VISIBLE
        resultadoTextView.text = ""
        numeroIntroducido.text.clear()
    }

    private fun generarNumeroAleatorio(): Int {
        return Random.nextInt(Constantes.MIN_RANDOM, Constantes.MAX_RANDOM + 1)
    }
}