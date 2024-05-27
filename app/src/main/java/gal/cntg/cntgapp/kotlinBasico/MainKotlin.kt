package gal.cntg.cntgapp.kotlinBasico

/*class MainKotlin {
}*/

//Función de entrada al programa. Ejecutamos en el triángulo a lado de main.
fun main() {
    print("Hola mundo Kt")
    var profesor:Participante = Participante("Vale", "https://github.com/Valexx55", "https://www.linkedin.com/in/valerianomoreno/" )
    var carlos:Participante = Participante("Carlos", "https://github.com/alfmanda", "https://es.linkedin.com/in/carlos-otero-bouzas")
    var pablo:Participante = Participante("Pablo Sánchez", "https://github.com/PabloSanchez87", "www.linkedin.com/in/pablosancheztorres")
    var jose:Participante = Participante("Jose", "https://github.com/joseEstevezRey","" )
    var daniel:Participante = Participante("Daniel", "", "")
    var alejandro:Participante = Participante("Alejandro", "", "")
    var joaquin:Participante = Participante("Joaquin", "", "")
    var xoel:Participante = Participante("Xoel", "https://github.com/xoelin25", "https://www.linkedin.com/in/xoel-%C3%A1lvarez-alonso-3b052a138/")
    var prada:Participante = Participante("Luis Prada", "https://github.com/gitluisprada", "https://www.linkedin.com/in")
    var pedro:Participante = Participante("Pedro Rodríguez ", "", "https://www.linkedin.com/in/pedroroan/")
    var marta:Participante = Participante("Marta de Guzmán", "https://github.com/MdGdC", "" )
    var marcos: Participante = Participante("Marcos", "https://github.com/MarksCore", "https://www.linkedin.com/in/marcos-c-p/")
    var alfonsoalvar = Participante("Alfonso Alvar", "https://github.com/alfonsoalvar", "https://www.linkedin.com/in/alfonsoalvar")
    var enmanuel:Participante = Participante("Enmanuel", "https://github.com/EnmaLlDev", "https://www.linkedin.com/in/enmanuel-ll-406a93240/" )
    var alejandroR:Participante = Participante("Alejandro", "https://github.com/AlejoRub","https://www.linkedin.com/in/alejandro-rubial-quelle-24bb87164/")

    var listaParticipantes = listOf(profesor, carlos, pablo, jose, daniel, alejandro, joaquin, xoel, prada, pedro, marta, marcos, alfonsoalvar, enmanuel, alejandroR)

    // forEach solicita un consumer, que viene siendo la función que le estamos pasando.
    // Consumer es un interfaz.
    // También valdría --> listaParticipantes.forEach{p -> println(p.nombre)}
    listaParticipantes.forEach({p -> println(p.nombre)})


    // Estamos usando toString overwrite en Participantes.kt
    listaParticipantes.forEach({p -> println(p.toString())})

    // Uso de DATACLASS en vez de Class a secas
    // Para tipo de datos sencillos que no tienen ninguna funcionalidad extra, que solo usamos los atributos básicos,
    // (beans) es mejor usar las dataclass pq ya tienen las funciones overwrite para un mejor uso.
    // toString, equals, ...
    var usuario =Usuario ("Ana", 23)
    print("Usuario "+ usuario.toString())
}

