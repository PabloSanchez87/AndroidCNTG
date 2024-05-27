package gal.cntg.cntgapp.kotlinBasico

class Participante (var nombre: String, var urlGithub: String, var urlLinkedin: String ){

    override fun toString(): String {  // Sobreescribimos la función para que nos devuelva como queremos en vez de su dirección
        return "Nombre = ${this.nombre}"
    }

}