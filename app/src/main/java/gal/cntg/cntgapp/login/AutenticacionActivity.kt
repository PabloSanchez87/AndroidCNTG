package gal.cntg.cntgapp.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import gal.cntg.cntgapp.R

class AutenticacionActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_autenticacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.firebaseAuth = FirebaseAuth.getInstance()
    }

    fun login(view: View) {
        var correo = findViewById<EditText>(R.id.editTextText3).text.toString()
        var password = findViewById<EditText>(R.id.editTextText4).text.toString()

        this.firebaseAuth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener { tarea ->
                if (tarea.isSuccessful) {
                    Toast.makeText(this, "Usuario autenticado.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "ERROR AL AUTENTICAR EL USUARIO! .", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}