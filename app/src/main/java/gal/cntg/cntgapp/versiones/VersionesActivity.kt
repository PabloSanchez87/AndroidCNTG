package gal.cntg.cntgapp.versiones

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import gal.cntg.cntgapp.R


class VersionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets }
    }

    fun obtenerVersionAndroid ():String
    {
        var nombreVersion:String? = null //? --> significa que nomberVersion puede ser String o null

        //TODO obtener la versión
        nombreVersion = when (Build.VERSION.SDK_INT)
        {
            Build.VERSION_CODES.Q -> "ANDROID Q 10"
            Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> "ANDROID U 14"
            else -> "Versión distinta a la 10 o 14"
        }

        return nombreVersion
    }

}