package gal.cntg.cntgapp.fechayhora

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.time.Year

/**
 * Creamos esta clase nueva para crear los DialogFragment, por eso debe implementearla.
 * Tenemos que sobreescribir el constructor.
 * Cuando tengamos el foco sobre la caja, usaremos esta clase para mostrarle el DialogFragment
 * que corresponda fecha u hora en este caso.
 * REVISAR SELECCION HORA, MUY PARECIDAS.
 * */
class SeleccionFecha: DialogFragment(), OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var miCalendario:Dialog
        var calendar: Calendar = Calendar.getInstance()
        var anio = calendar.get(Calendar.YEAR)
        var mes = calendar.get(Calendar.MONTH)
        var dia = calendar.get(Calendar.DATE)

        miCalendario = DatePickerDialog(requireActivity(), this, anio, mes, dia)

        return miCalendario
    }
    override fun onDateSet(view: DatePicker?, anio: Int, mes: Int, dia: Int) {
        var fechaFinal = "$dia/${mes+1}/$anio" //OJO --> El mes lo devuelve del 0 a 11
        Log.d("CNTG APP", "FECHA SELECIONADA: $fechaFinal")
        (activity as SeleccionFechaYHoraActivity).actualizarFechaSeleccionada(fechaFinal)
    }


}