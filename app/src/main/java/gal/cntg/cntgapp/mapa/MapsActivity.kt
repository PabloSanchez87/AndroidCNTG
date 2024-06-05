package gal.cntg.cntgapp.mapa

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap   // variable miembro. Es la variable que va a representar el mapa.
    private lateinit var binding: ActivityMapsBinding

    // objetos que usamos en la locacition
    lateinit var locationManager: LocationManager // Para ver si tengo activo o no el GPS -> es un SERVICIO DEL SISTEMA --> IMPORTANTE
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient // nos va a dar la ubicación. Admite distintos proveedores.
    lateinit var locationRequest: LocationRequest // petición de ubicación.
    lateinit var locationCallback: LocationCallback // respuesta a petición de ubicación

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)  // pedimos el mapa y cuando este lista, llamamos a onMapReady sobre escrita.
        this.locationManager = getSystemService(LOCATION_SERVICE)  as LocationManager
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f)) //Según el valor cambia el zoom. Buscar docu.
    }

    fun mostrarUbicacionMapa(view: View) {
        Log.d("CNTG_APP", "Mostrar ubicación mapa...")

        // Solicitamos los permisos en ejecución para location (que se considera permiso PELIGROSO) --> A partir de la v6.
        // Pq después el usuario los puede sacar desde los ajustes del teléfono, por eso tenemos que comprobarlo.
        // Opción perdirlos siempre. Si android ve que ya los tienes ya no lo solicita.
        // OJO MANIFEST DE ANDROID. ///
        requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 550)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Log.d("CNTG_APP", "El usuarios nos ha concedido permiso.")
            if(gpsEstaActivado()){
                accederALaUbicacionGPS()
            } else {
                solicitarActivacionGPS()
            }
        }else{
            Log.d("CNTG_APP", "El usuario NO nos ha concedido permiso.")
            Toast.makeText(this, "SIN PERMISO PARA ACCEDER A SU UBICACIÓN", Toast.LENGTH_LONG).show()
        }
    }

    private fun gpsEstaActivado(): Boolean {
        var activadoGps : Boolean = false

        activadoGps = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        return activadoGps
    }


    private fun solicitarActivacionGPS() {
        // Tenemos q lanzar una actividad de ajustes y recibir el resultado. Usando un INTENT IMPLICITO.
        val intentPedirGps = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS) // Con esto vamos a ajustes para pedir que active el GPS
        startActivityForResult(intentPedirGps, 55) // Deprecado. Un código 55(aleatorio) para ponerle nombre a la petición.
    }

    // Esta función será invocada a la vuelta de los ajuste de ubicación y traerá y la decisón
    // del usuario en el intent lanzado por startActivityForResult.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (gpsEstaActivado()){
            Log.d("CNTG_APP", "El usuario HA ACTIVADO LA UBICACIÓN")
            accederALaUbicacionGPS()
        } else {
            Log.d("CNTG_APP", "El usuario NO ha ACTIVADO LA UBICACIÓN.")
            Toast.makeText(this, "GPS DESACTIVADO - SIN PERMISO PARA ACCEDER A SU UBICACIÓN", Toast.LENGTH_LONG).show()
        }
    }


    private fun accederALaUbicacionGPS() {
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        //Creamos la petición
        this.locationRequest = LocationRequest
            .create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // alta precisión
            .setInterval(5000) // cada 5 segundo actualiza.

        // Este es el objeto que recibe el resultado.
        locationCallback = object : LocationCallback()
        {
            override fun onLocationResult(resultadoUbicacion: LocationResult) {
                //super.onLocationResult(p0)
                if (resultadoUbicacion != null){                        // estamos accediendo a la ubicación en si.
                    Log.d("CNTG_APP", "Ubicación obtenida ${resultadoUbicacion.lastLocation}")

                    /* CODIGO PROPIO PARA MOSTRAR LA UBICACIÓN.
                    // Elimina el marcador anterior si lo hay y añade uno nuevo en la nueva ubicación
                    val nuevaUbicacion = resultadoUbicacion.lastLocation
                    val nuevaLatLng = LatLng(nuevaUbicacion.latitude, nuevaUbicacion.longitude)
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(nuevaLatLng))

                    // Mueve la cámara para centrarse en la nueva ubicación
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nuevaLatLng, 15f))*/

                    this@MapsActivity.fusedLocationProviderClient.removeLocationUpdates(locationCallback) // para que pare de actualizar la búsqueda
                }
            }
        }

        // Acceso al GPS. De primeras marca en rojo. Le damos a addpermisiónCheck en el asistente y nos hace este bucle if.
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        // Acceso al GPS. De primeras marca en rojo, pero el asistente nos saca el anterior if.
        this.fusedLocationProviderClient.requestLocationUpdates(this.locationRequest, this.locationCallback, null)
    }

}