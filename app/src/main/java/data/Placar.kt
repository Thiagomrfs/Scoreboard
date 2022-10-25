package data

import java.io.Serializable

data class Placar(
    var nome_partida : String,
    var timeA : String,
    var timeB : String,
    var resultado : String,
    var resultadoLongo : String,
    var has_timer : Boolean,
    var quarto_atual : Int
):Serializable
