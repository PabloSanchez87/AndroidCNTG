package gal.cntg.cntgapp.imc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.util.Constantes
import kotlin.system.exitProcess

class IMCActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imcactivity2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Listener - Callback. Casi todo el funcionamiento de android se basa en listener -> callback
    // Unit es lo que devuelve, igual que void(no devuelve nada)
    fun calcularIMC(view: View):Unit   // onclickListener
    {
        Log.d("MiApp", "Botón CALCULAR  ha sido tocado.")

        // TODO. Calcular imc= peso/altura al cuadrado
        // 1. obtener peso
        val etpeso = findViewById<EditText>(R.id.Peso)
        val peso = etpeso.text.toString().toFloat()

        // 2. obtener altura
        val etaltura = findViewById<EditText>(R.id.Altura)
        val altura = etaltura.text.toString().toFloat()

        // 3.calcular imc
        //val imc = peso / (altura*altura)

        // Hacemos el calculo usamos la clase del objeto creada en imc.kt
        val objetoImc = IMC(peso, altura)
        val imc = objetoImc.calcularIMC()

        // 4. devolver resultado.
        // Toast - típico mensaje temporal que aparece en pantalla.
        val aviso = Toast.makeText(this,"Su IMC es $imc", Toast.LENGTH_LONG)
        aviso.show()

        // TODO transistar a la actividad resultado, para mostras una foto.
        // el tipo de imc que tiene el usuario.
        val intentResultado : Intent = Intent(this, ResultadoIMCActivity::class.java)
            // Le pasamos el objeto en el que estamos y el objeto de la clase a la que queremos ir.

        // Guardo en el saco de intent el resultado que quiero parar a la otra activity.
        intentResultado.putExtra("IMC_RESULTADO", imc)

        startActivity(intentResultado) //lanzo a otra pantalla de resultado EXPLICITO.
        // El implicito sería por ejemplo el que generamos cuando compartimos algo y nos da donde
        // elegir si whatsapp, instagram,...

    }

    // Para dibujar mi menu en la appbar(toolbar) hay que sobreescribir el método onCreateOptionMenu
    // Inflar --> es pasar de xml a visual. Se convierte en objeto.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_imc, menu) // Vamos a inflar nuestro menu y anclarlo al padre.
        return super.onCreateOptionsMenu(menu)
    }

    // este método será invocado por Android cuando el usaurio toque alguna opcion del toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("Cntg App","Ha tocado el menú.")

        when (item.itemId){
            R.id.opcionLimpiar ->{
                Log.d("Cntg App","Ha tocado el limpiar.")
                limpiarFormulario()
            }


            R.id.opcionSalir -> {
                Log.d("Cntg App","Ha tocado el salir.")
                salir()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    /**
    finishAffinity()    // finishAffinity() --> Para salir de la aplicación
    finish()            // finish() --> Para salir de la aplicación
     **/
    // Función para mostrar un AlertDialog usando el patrón Builder.
    private fun salir() {
        // Creación del AlertDialog.Builder y configuración de sus propiedades.
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("SALIR") // Título del diálogo.
            .setMessage("¿Desea salir?") // Mensaje del diálogo.
            // Configuración del botón negativo (NO).
            .setNegativeButton("NO") { dialog, _ ->  // en clase usamos { dialog, opcion --> nos obliga la definición de la funcion
                dialog.cancel() // Cierra el diálogo cuando se pulsa el botón NO.
            }
            // Configuración del botón positivo (SÍ). // null, Se establece el listener más adelante.
            .setPositiveButton("SÍ", null) /** { _, _ ->  // en clase usamos { dialog, opcion --> nos obliga la definición de la funcion
                this.finish() // Finaliza la actividad actual cuando se pulsa el botón SÍ.
            }**/
            // Configuración del botón neutral (Minimizar).
            .setNeutralButton("MINIMIZAR") { _, _ ->  // En clase usamos { dialog, opcion -->
                moveTaskToBack(true) // Minimiza la actividad actual cuando se pulsa el botón MINIMIZAR.
            }
            // Creación del AlertDialog a partir del Builder configurado.
            .create()

        // Muestra el AlertDialog en pantalla.
        alertDialog.show()

        /** Esta parte la usamos para deshabilitar temporalmente la opción de sí, para no cerrar sin querer la app*/
        // Obtener el botón "SÍ" y deshabilitarlo inicialmente.
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        // Usar un Handler para habilitar el botón después de 5 segundos.
        Handler(Looper.getMainLooper()).postDelayed({
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
        }, 3000) // 3 segundos
        // Configurar el listener del botón "SÍ" después de crear el diálogo.
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            finishAndRemoveTask()  // Cierra la actividad actual y elimina la tarea asociada.
        }
        /** *******************************************************************************************************/
    }

    private fun limpiarFormulario() {
        findViewById<EditText>(R.id.Peso).text.clear()
        findViewById<EditText>(R.id.Altura).text.clear()
    }


}