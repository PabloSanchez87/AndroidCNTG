package gal.cntg.cntgapp.fechayhora

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment


class SeleccionHora: DialogFragment(), OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var reloj: Dialog
        var hora: Int
        var minutos: Int

        // Usamos la class Calendar, pero actualmente ya existen clases más modernas.
        var calendario: Calendar = Calendar.getInstance() // ne da ka fecha/hora actuales del sistema.

        hora = calendario.get(Calendar.HOUR_OF_DAY)
        minutos = calendario.get(Calendar.MINUTE)

        /**
         * TimePickerDialog -->s un componente en Android que permite al usuario seleccionar una hora (horas y minutos) en un formato de reloj.
         * Es particularmente útil cuando se desea que el usuario elija una hora específica para realizar alguna acción.
         *      - Contexto (this): El contexto de la actividad o fragmento en el que se muestra el TimePickerDialog.
         *      - Listener (TimePickerDialog.OnTimeSetListener): Este es un callback que se ejecuta
         *              cuando el usuario selecciona una hora. Los parámetros del callback incluyen el TimePicker que se utilizó
         *              y las horas y minutos seleccionados (selectedHour y selectedMinute).
         *      - Hora y minuto predeterminados (hour, minute): Estos definen la hora que se muestra inicialmente cuando se abre el diálogo.
         *      - Formato de 24 horas (true): Define si se usa el formato de 24 horas (true) o el formato de 12 horas con AM/PM (false).
         * */
        // Al poner this en el listener, tenemos que hacer que la clase implemente OnTimeSetListener y debemos
        // sobreescribir onTimeSet.
        reloj = TimePickerDialog(requireActivity(),this, hora, minutos, true)

        return reloj
        // el return inicial --> return super.onCreateDialog(savedInstanceState)
    }

    // Al poner this en el listener, tenemos que hacer que la clase implemente OnTimeSetListener y debemos
    // sobreescribir onTimeSet.
    override fun onTimeSet(reloj: TimePicker?, horaSeleccionada: Int, minutosSeleccionados: Int) {
        val horaFinal: String
        val hora: String
        val minuto: String

        // si es menor de 10, concatenamos un 0 al princpio para que se lea mejor
        hora = if (horaSeleccionada<10) "0$horaSeleccionada" else horaSeleccionada.toString()
        minuto =  if (minutosSeleccionados<10) "0$minutosSeleccionados" else minutosSeleccionados.toString()
        horaFinal = "$hora:$minuto" // FORMATO HH:MM --> se puede usar formato java para no hacerlo tan a mano.

        // activity es la actividad padre desde donde nace a la que pertenece este fragmente.
        // los gramente/dialosgFragment no tiene vida separada de una activity
        // haciendo el casteo tenemos esa variable instanciada para tener acceso al método para mostrar la hora
        var ventanaPadre = activity as SeleccionFechaYHoraActivity
        ventanaPadre.actualizarHoraSeleccionada(horaFinal)

        Log.d("CNTG APP", "HORA FINAL = $horaFinal")
    }
}
