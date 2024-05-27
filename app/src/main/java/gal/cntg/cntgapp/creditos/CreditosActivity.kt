package gal.cntg.cntgapp.creditos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gal.cntg.cntgapp.R
import gal.cntg.cntgapp.kotlinBasico.Participante



class CreditosActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*enableEdgeToEdge()*/
        setContentView(R.layout.activity_creditos)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        // Crear la lista de participantes
        val participantes = listOf(
            Participante("Vale", "https://github.com/Valexx55", "https://www.linkedin.com/in/valerianomoreno/"),
            Participante("Carlos", "https://github.com/alfmanda", "https://es.linkedin.com/in/carlos-otero-bouzas"),
            Participante("Pablo Sánchez", "https://github.com/PabloSanchez87", "www.linkedin.com/in/pablosancheztorres"),
            Participante("Jose", "https://github.com/joseEstevezRey", ""),
            Participante("Daniel", "", ""),
            Participante("Alejandro", "", ""),
            Participante("Joaquin", "", ""),
            Participante("Xoel", "https://github.com/xoelin25", "https://www.linkedin.com/in/xoel-%C3%A1lvarez-alonso-3b052a138/"),
            Participante("Luis Prada", "https://github.com/gitluisprada", "https://www.linkedin.com/in"),
            Participante("Pedro Rodríguez ", "", "https://www.linkedin.com/in/pedroroan/"),
            Participante("Marta de Guzmán", "https://github.com/MdGdC", ""),
            Participante("Marcos", "https://github.com/MarksCore", "https://www.linkedin.com/in/marcos-c-p/"),
            Participante("Alfonso Alvar", "https://github.com/alfonsoalvar", "https://www.linkedin.com/in/alfonsoalvar"),
            Participante("Enmanuel", "https://github.com/EnmaLlDev", "https://www.linkedin.com/in/enmanuel-ll-406a93240/"),
            Participante("Alejandro", "https://github.com/AlejoRub", "https://www.linkedin.com/in/alejandro-rubial-quelle-24bb87164/")
        )

        this.recyclerView = findViewById(R.id.recyclerView)
        //TODO me queda asociar a este Recycler su Adapter

        // Crear instancia del Adapter con la lista de participantes
        val adapter = AdapterParticipantes(participantes)

        // Asignar el Adapter al RecyclerView
        recyclerView.adapter = adapter

        // Establecer un LayoutManager para el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}