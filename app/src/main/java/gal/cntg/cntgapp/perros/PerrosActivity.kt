package gal.cntg.cntgapp.perros

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.databinding.ActivityPerrosBinding


// LA propia clase escucha los eventos de selección del spinner. Por eso tmb implementa
// AdapterView.OnItemSelectedListener
class PerrosActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var razaSeleccionada: String = ""
    lateinit var binding: ActivityPerrosBinding // Se genera automáticamente la clase ActivityPerrosBinding
    val arrayRazas =
        arrayOf("affenpinscher", "african", "airedale", "akita", "appenzeller", "australian")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPerrosBinding.inflate(layoutInflater) //Inicializamos el binding.
        setContentView(binding.main)  //--> hemos cambiado: setContentView(R.layout.activity_perros)

        // Funciona con 3 parámetros.
        //      Contexto: this
        //      Layaout: El de serie es android.R.layout.simple_spinner_item (nos referimos a los recursos del sistema)
        //      Le pasamos el array a mostrar.
        val adapterSpinner =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayRazas)

        // le damos un estilo al spinner para que se vea mejor.
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerRazas.adapter =
            adapterSpinner // Asignamos al espiner el adapter que le proporciona los datos.

        // Escuchamos cuando selecciona. Queremos que esta propia clase sea la que escuche. Revisar cabecera de la clase.
        binding.spinnerRazas.onItemSelectedListener = this
    }


    fun buscarFotos(view: View) {
        // class.java tiene como los metadatos de la actividad/clase/atributos.
        val intentGaleria = Intent(
            this,
            GaleriaPerrosActivity::class.java
        ) // le indicamos a donde queremos transitar.
        intentGaleria.putExtra("raza", this.razaSeleccionada)
        startActivity(intentGaleria)

    }


    override fun onItemSelected(p0: AdapterView<*>?, opcionTocada: View?, p2: Int, p3: Long) {
        // Se produce un callback la primera vez que se carga el spinner, aunq el usuario no haya seleccionado nada cuando se invoca el método.
        this.razaSeleccionada = (opcionTocada as TextView).text.toString()
        Log.d("CNTGAPP", "$razaSeleccionada")
        this.mostrarSnackBar()
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        // nada que hacer
        // Lo tendría que implementar si una opción de las disponibles en el spinner,
        // deja de estarlo (si pudiera eliminar alguna opción seleccionable)
    }

    fun mostrarSnackBar(): Unit{
        var snackbar: Snackbar = Snackbar.make(binding.main, "LISTADO RECUPERADO", BaseTransientBottomBar.LENGTH_LONG)

        //podemos poner una acción a ese snackbar, con la función que queramos.
        snackbar.setAction("Cerrar"){vista -> Log.d("CNTG APP", "SnackBar tocado.")}
        snackbar.setTextColor(getColor(R.color.white)) // podemos establecerle un color.

        //mostramos el snackbar.
        snackbar.show()

    }
}