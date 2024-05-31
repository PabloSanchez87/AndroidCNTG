package gal.cntg.cntgapp.perros

import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  Esta clase encapsula la recogida de datos del Dog Api (comunicacion HTTP)
 *  USAMOS RETROFIT
 * */

interface PerrosServices {

    //https://dog.ceo/api/breed/african/images
    // {raza} lo usamos para sustituir en la api. Debemos indicárselo en la función con @path
    @GET("api/breed/{raza}/images") // suspend: puede quedar esperando una respuesta
    suspend fun listarPerrosPorRaza(@Path("raza") raza:String):ListadoPerros

    // ListadoPerros --> es una dataClass generada con Json to kotlin y la respuesta generada por postman
}