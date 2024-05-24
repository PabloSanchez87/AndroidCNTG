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
    // Variables para el número aleatorio y los intentos restantes
    private var numeroAleatorio: Int = 0
    private var intentos: Int = Constantes.MAX_INTENTOS

    // Variables para las vistas
    private lateinit var numeroIntroducido: EditText
    private lateinit var probarSuerte: Button
    private lateinit var resultadoTextView: TextView
    private lateinit var reiniciarBoton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_number)

        inicializarVistas() // Inicializamos las vistas

        // Restaurar el estado de la instancia si existe, de lo contrario, configurar un nuevo juego
            // Si existe un estado guardado, restauramos el número aleatorio.
            // Si no, generamos un nuevo número aleatorio.
        numeroAleatorio = savedInstanceState?.getInt("numeroAleatorio") ?: generarNumeroAleatorio()
            // Restauramos el número de intentos restantes desde el estado guardado si existe.
            // Si no existe, usamos el valor máximo de intentos definido en Constantes.
        intentos = savedInstanceState?.getInt("intentos") ?: Constantes.MAX_INTENTOS
            // Restauramos el texto del resultado desde el estado guardado si existe.
            // Si no existe, dejamos el TextView de resultado vacío.
        resultadoTextView.text = savedInstanceState?.getString("resultado") ?: ""

        // Configurar la visibilidad de los botones basándonos en el estado restaurado
            // Determinamos la visibilidad del botón de reinicio basándonos en el estado guardado.
            // Si el estado guardado indica que el botón de reinicio era visible, lo hacemos visible.
            // Si no, lo hacemos invisible.
        reiniciarBoton.visibility = if (savedInstanceState?.getBoolean("reiniciarVisible") == true) View.VISIBLE else View.INVISIBLE
            // Determinamos la visibilidad del botón de probarSuerte basándonos en el estado guardado.
            // Si el estado guardado indica que el botón de reinicio era visible, hacemos el botón de chequeo invisible.
            // Si no, lo hacemos visible.
        probarSuerte.visibility = if (savedInstanceState?.getBoolean("reiniciarVisible") == true) View.INVISIBLE else View.VISIBLE
            // Habilitamos o deshabilitamos el botón de probarSuerte basándonos en el estado guardado.
            // Si el estado guardado indica que el botón de reinicio era visible, deshabilitamos el botón de chequeo.
            // Si no, lo habilitamos.
        probarSuerte.isEnabled = savedInstanceState?.getBoolean("reiniciarVisible") != true

        // Configurar los listeners de los botones
        configurarListeners()

        // Si no hay estado restaurado, configurar un nuevo juego.
            // Si el savedInstanceState es nulo, significa que no estamos restaurando un estado anterior, por lo que
            // llamamos a configurarJuego() para inicializar el juego con un nuevo número aleatorio y restablecer
            // los valores predeterminados.
        if (savedInstanceState == null) {
            configurarJuego()
        }
    }

    private fun inicializarVistas() {
        numeroIntroducido = findViewById(R.id.numberIn)
        probarSuerte = findViewById(R.id.boton)
        resultadoTextView = findViewById(R.id.resultado)
        reiniciarBoton = findViewById(R.id.reiniciar)
    }

    private fun configurarListeners() {
        // Configurar los listeners para los botones
            // Establece un listener para el botón de comprobación.
            // Cuando se hace clic en este botón, se llama a la función comprobarNumero().
        probarSuerte.setOnClickListener { comprobarNumero() }
            // Establece un listener para el botón de reinicio.
            // Cuando se hace clic en este botón, se llama a la función reiniciarJuego().
        reiniciarBoton.setOnClickListener { reiniciarJuego() }
    }

    private fun configurarJuego() {
        // Configurar el juego inicializando el número aleatorio y configurando botones a enseñar.
        numeroAleatorio = generarNumeroAleatorio()
        reiniciarBoton.visibility = View.INVISIBLE
    }

    private fun comprobarNumero() {
        val userInput = numeroIntroducido.text.toString()
        if (userInput.isNotEmpty()) { // Corrección: debía ser isNotEmpty en lugar de isEmpty
            val userNumber = userInput.toInt()
            intentos--
            val result: String
            if (userNumber == numeroAleatorio) {
                result = "Correcto! Has acertado! El número era $numeroAleatorio"
                probarSuerte.isEnabled = false
                reiniciarBoton.visibility = View.VISIBLE
            } else {
                result = if (intentos > 0) {
                    if (userNumber > numeroAleatorio) {
                        "Has fallado! Vuelva a intentarlo. El número es menor. Intentos restantes: $intentos."
                    } else {
                        "Has fallado! Vuelva a intentarlo. El número es mayor. Intentos restantes: $intentos."
                    }
                } else {
                    "Game Over!! El número correcto era $numeroAleatorio. Te has quedado sin intentos."
                }
                if (intentos == 0) {
                    // Mostrar el botón de reinicio cuando se agoten los intentos
                    reiniciarBoton.visibility = View.VISIBLE
                    probarSuerte.visibility = View.INVISIBLE
                }
            }
            resultadoTextView.text = result
            numeroIntroducido.text.clear() // Vaciamos la caja del EditText
        } else {
            Toast.makeText(this, "Introduce un número", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reiniciarJuego() {
        // Reiniciar el juego configurando un nuevo número aleatorio y restableciendo los intentos
        numeroAleatorio = generarNumeroAleatorio()
        intentos = Constantes.MAX_INTENTOS
        probarSuerte.isEnabled = true
        reiniciarBoton.visibility = View.INVISIBLE
        probarSuerte.visibility = View.VISIBLE
        resultadoTextView.text = ""
        numeroIntroducido.text.clear()
    }

    private fun generarNumeroAleatorio(): Int {
        // Generar un número aleatorio dentro del rango definido en Constantes
        return Random.nextInt(Constantes.MIN_RANDOM, Constantes.MAX_RANDOM + 1)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Guardar el estado actual de la actividad
        super.onSaveInstanceState(outState)
        outState.putInt("numeroAleatorio", numeroAleatorio)
        outState.putInt("intentos", intentos)
        outState.putString("resultado", resultadoTextView.text.toString())
            // Controlamos si tenemos que enseñar el botón de reiniciar o no.
        outState.putBoolean("reiniciarVisible", reiniciarBoton.visibility == View.VISIBLE)
    }
}