package gal.cntg.cntgapp.busquedaActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import gal.cntg.cntgapp.R

class BusquedaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busqueda)
    }

    fun buscar(view: View) {

        val busqueda:String =  findViewById<EditText>(R.id.cajaBusquedaTexto).text.toString()
        //INTENT PARA VER LA PÁGINA WEB
        val url = "https://www.google.com/search?q="+busqueda
        val uri = Uri.parse(url)//paso de string a uri
        val intentImplicito = Intent(Intent.ACTION_VIEW, uri )//QUIERO VER, UNA WEB
        if (intentImplicito.resolveActivity(packageManager)!=null)
        {
            startActivity(intentImplicito)
        } else {
            Toast.makeText(this, "NO HAY UNA APP PARA VER WEBS", Toast.LENGTH_LONG).show()
        }
    }
}