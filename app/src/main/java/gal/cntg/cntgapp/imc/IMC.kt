package gal.cntg.cntgapp.imc


class IMC (val peso:Float, val altura:Float) {

    // Clase normal de kt, no de android.
    fun calcularIMC():Float{
        var imcNumerico :Float = 0f

        imcNumerico = this.peso / (this.altura*this.altura)

        return imcNumerico
    }


}