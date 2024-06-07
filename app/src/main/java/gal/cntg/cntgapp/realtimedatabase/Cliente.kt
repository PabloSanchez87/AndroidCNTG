package gal.cntg.cntgapp.realtimedatabase

import java.io.Serializable

// Atributos que se correspondan con los atributos del formulario a guardar en Firabase
data class Cliente(val edad:Long, val localidad:String, val nombre:String,
                   val email:String, val nacionalidad:String, var clave:String="") :Serializable
