package gal.cntg.cntgapp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView


class MenuPrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    var menuVisible = false // Variable de control para saber si el menu está abierto o no.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        this.drawerLayout  = findViewById<DrawerLayout>(R.id.drawerView)
        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        // para dibujar la hamburguesa del menú
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Dibuja el icono del menu.
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_density_medium_24) // Le indicamos el icono a mostrar.

        // Tenemos que sobreescribir dos métodos.
        //          Debemos implementar tmb NavigationView.OnNavigationItemSelectedListener
        // - onOptionsItemSelected // onNavigationItemSelected
        this.navigationView.setNavigationItemSelectedListener(this)
    }

    /**
     *  Este método se invoca al tocar la hamburguesa.
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId)
        {
            android.R.id.home -> {
                Log.d("CNTG_APP", "Botón hamburguesa tocado.")
                if (this.menuVisible){
                    this.drawerLayout.closeDrawers()
                } else {
                    this.drawerLayout.openDrawer(GravityCompat.START) // así se dibuja de izq a derecha.
                }
                this.menuVisible = !this.menuVisible // De esta manera abrirá el menú o lo cierra según proceda con la negación.
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("CNTG_APP", "Submenú tocado.")

        // TODO definir los intents según la opción, lanzando la actividad correspondiente.


        this.drawerLayout.closeDrawers() // Cierro el menú sea cual sea la opción tocada.

        return true
    }


}