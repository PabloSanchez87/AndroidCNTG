package gal.cntg.cntgapp.imc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import gal.cntg.cntgapp.R

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

        //Guardo en el saco de intent el resultado que quiero parar a la otra activity.
        intentResultado.putExtra("IMC_RESULTADO", imc)

        startActivity(intentResultado) //lanzo a otra pantalla de resultado EXPLICITO.
        // El implicito sería por ejemplo el que generamos cuando compartimos algo y nos da donde
        // elegir si whatsapp, instagram,...

    }


}