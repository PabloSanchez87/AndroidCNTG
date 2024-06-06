package gal.cntg.cntgapp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import gal.cntg.cntgapp.R

/**
 * ACTIVIDAD DE BIENVENIDA..
 *      - NUEVO REGISTRO
 *      - ACCESO
 * */
class MenuLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_login)

        supportActionBar?.hide() // ocultamos la barra superior APPBar/ToolBar
        window.decorView.systemUiVisibility =View.SYSTEM_UI_FLAG_FULLSCREEN // pantalla completa

    }

    fun nuevaCuenta(view: View) {
        val intentNueva = Intent(this, RegistroNuevoUsuarioActivity::class.java)
        startActivity(intentNueva)
    }


    fun acceder(view: View) {
        val intentLogin = Intent(this, AutenticacionActivity::class.java)
        startActivity(intentLogin)
    }
}