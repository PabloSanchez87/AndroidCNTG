package gal.cntg.cntgapp.perros

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.databinding.ActivityPerrosBinding
import gal.cntg.cntgapp.kotlinBasico.main

class PerrosActivity : AppCompatActivity() {

    lateinit var binding: ActivityPerrosBinding // Se genera automáticamente la clase ActivityPerrosBinding
    val arrayRazas = arrayOf("affenpinscher", "african", "airedale", "akita", "appenzeller", "australian")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPerrosBinding.inflate(layoutInflater) //Inicializamos el binding.
        setContentView(binding.main)  //--> hemos cambiado: setContentView(R.layout.activity_perros)

        // Funciona con 3 parámetros.
        //      Contexto: this
        //      Layaout: El de serie es android.R.layout.simple_spinner_item (nos referimos a los recursos del sistema)
        //      Le pasamos el array a mostrar.
        val adapterSpinner = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayRazas)

        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // le damos un estilo al spinner para que se vea mejor.

        binding.spinnerRazas.adapter = adapterSpinner // Asignamos al espiner el adapter que le proporciona los datos.

    }

    fun buscarFotos(view: View) {}
}