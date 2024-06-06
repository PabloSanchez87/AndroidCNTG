package gal.cntg.cntgapp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import gal.cntg.cntgapp.R

class RegistroNuevoUsuarioActivity : AppCompatActivity() {

    //Para conectarnos con el servicio de Auth de Firabase
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_nuevo_usuario)

        this.firebaseAuth = FirebaseAuth.getInstance()

    }

    fun registrarNuevoUsuario(view: View) {
        var correNuevo = findViewById<EditText>(R.id.editTextText).text.toString()
        var passwordNuevo = findViewById<EditText>(R.id.editTextText2).text.toString()

        // esto esta programado al estilo de las promesas. Funciones async que invocan a la función cuando se completa.
        this.firebaseAuth.createUserWithEmailAndPassword(correNuevo, passwordNuevo)
            .addOnCompleteListener { tarea ->
                if (tarea.isSuccessful) {
                    Toast.makeText(this, "NUEVO USARIO REGISTRADO.", Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(Intent(this, AutenticacionActivity::class.java))
                } else {
                    Toast.makeText(this, "ERROR! NO SEA REGISTRADO EL USUARIO.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }


}