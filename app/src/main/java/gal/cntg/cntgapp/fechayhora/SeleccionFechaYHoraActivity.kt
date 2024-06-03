package gal.cntg.cntgapp.fechayhora

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.databinding.ActivitySeleccionFechaYhoraBinding

class SeleccionFechaYHoraActivity: AppCompatActivity(), OnFocusChangeListener {

    lateinit var binding: ActivitySeleccionFechaYhoraBinding // class binding generada.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeleccionFechaYhoraBinding.inflate(layoutInflater) // inflamos binding

        setContentView(binding.main)
        //setContentView(R.layout.activity_seleccion_fecha_yhora)

        // cuando la caja coja el foco, el cursor esté ahí, se llama a la función
        //      necesitamos que esta clase implemente el onFocusChangeListener
        //      --> Por eso estamos sobreescribiendo el método onFocusChange abajo.
        binding.cajaHora.onFocusChangeListener = this
        binding.cajaFecha.onFocusChangeListener = this

    }

    // tanto si las cajas ganan el foco, como si lo pierden, se invoca a esta función.
    override fun onFocusChange(caja: View?, tieneFoco: Boolean) {

        if (tieneFoco) {
            Log.d("CNTG APP", "Ha cambiado el foco")
            when (caja?.id){
                R.id.cajaHora -> {
                    Log.d("CNTG APP", "La caja hora ha ganado el foco.")

                    //Creamos el diálogo/fragmento.
                    val dialogoFragmentoReloj: DialogFragment = SeleccionHora()
                    // Lo mostramos/visualizamos
                    // supportFragmentManager es el encargado de gestionar el ciclo de vida del fragmento.
                    dialogoFragmentoReloj.show(supportFragmentManager, "RELOJ")
                }
                R.id.cajaFecha -> {
                    Log.d("CNTG APP", "La caja fecha ha ganado el foco.")
                    //Creamos el diálogo/fragmento.
                    val dialogoFragmentoCalendario: DialogFragment = SeleccionFecha()
                    // Lo mostramos/visualizamos
                    // supportFragmentManager es el encargado de gestionar el ciclo de vida del fragmento.
                    dialogoFragmentoCalendario.show(supportFragmentManager, "FECHA")
                }
            }

        }
    }

    // Creamos este método para que se muestre por pantalla la hora seleccionada.
    // Lo usamos para poder tener acceso al contexto en otra clase.
    fun actualizarHoraSeleccionada(hora:String){
        this.binding.cajaHora.setText(hora)
    }

    fun actualizarFechaSeleccionada(fecha:String){
        this.binding.cajaFecha.setText(fecha)
    }
}