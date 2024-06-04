package gal.cntg.cntgapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import gal.cntg.cntgapp.busquedaActivity.BusquedaActivity
import gal.cntg.cntgapp.creditos.CreditosActivity
import gal.cntg.cntgapp.fechayhora.SeleccionFechaYHoraActivity
import gal.cntg.cntgapp.imc.IMCActivity
import gal.cntg.cntgapp.perros.PerrosActivity
import gal.cntg.cntgapp.productos.ProductosActivity
import gal.cntg.cntgapp.searchNumber.SearchNumberActivity
import gal.cntg.cntgapp.tabs.TabsActivity
import gal.cntg.cntgapp.versiones.VersionesActivity
import gal.cntg.cntgapp.webview.WebActivity


class MenuPrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var esNoti = false
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

    override fun onNavigationItemSelected(opcionMenu: MenuItem): Boolean {
        Log.d("CNTG_APP", "Submenú tocado.")

        // Inicializamos una variable para guardar la activity destino.
        var objetoClass: Class<*>? = null

        // TODO definir los intents según la opción, lanzando la actividad correspondiente.
        when (opcionMenu.order)
        {
            0 -> { objetoClass = VersionesActivity::class.java}
            1 -> {
                objetoClass = WebActivity::class.java
                    // Aquí estamos provocando que el sistema tenga que decidir.
                    //      Para probar debemos comentar el otro intent de abajo.
                    //      Se ha incluido in intent filter en el android manifest
                    //val uri = Uri.parse("https://cntg.xunta.gal/")
                    //val intentImplicito = Intent(Intent.ACTION_WEB_SEARCH, uri)
                    //startActivity(intentImplicito)
            }
            2 -> {objetoClass = CreditosActivity::class.java}
            3 -> {objetoClass = IMCActivity::class.java}
            4 -> {objetoClass = BusquedaActivity::class.java}
            5 -> {objetoClass = SeleccionFechaYHoraActivity::class.java}
            6 -> {objetoClass = SearchNumberActivity::class.java}
            7 -> {objetoClass = PerrosActivity::class.java}
            8 -> {objetoClass = ProductosActivity::class.java}
            9 -> {objetoClass = TabsActivity::class.java}
            10 -> {
                esNoti = true
                Notificaciones.lanzarNotificacion(this)
            }
        }
        // Si ha pulsado notificación reseteamos el valor de la flag esNoti
        // para que se pueda seguir usando el menú aunque no entres en la notificación.
        if(esNoti){
           esNoti = false
        }
        else  {
            var intent = Intent(this, objetoClass)
            startActivity(intent)
        }

        this.drawerLayout.closeDrawers() // Cierro el menú sea cuál sea la opción tocada.

        return true
    }
}