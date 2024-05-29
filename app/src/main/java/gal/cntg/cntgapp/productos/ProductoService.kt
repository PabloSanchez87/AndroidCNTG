package gal.cntg.cntgapp.productos

import retrofit2.http.GET

/**
 * Estamos usando Retrofit.
 * Esta clase, encapsula la comunicación HTTP
 * Definimos la interface y definimos las operaciones que podemos hacer.
 * Que queremos?
 *  - Recibir --> Petición GET
 *  - Enviar -->
 * */

// En la interface sólo definimos la cabecera de la función. Retrofit genera el cuerpo.
interface ProductoService {

    /**
     *  @GET (retrofit)
     * suspend --> "async" significa que la función se puede detener, se puede quedar en segundo plano
     *              por la propia forma de ser del protocolo HTTP, no puede quedar bloqueado el
     *              programa esperando una respuesta.
     *              · Se está generando un programa pequeño pendiente a esperar de la respuesta.
     *              · Estamos generando un hilo que se encarga de gestionar eso.
     *              · Es como el "async"
     *
    */
    @GET("miseon920/json-api/products")
    suspend fun obtenerProductos():ListadoProductos

}