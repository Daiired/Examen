package com.example.examen.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Lugares (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    var nombre:String,
    var orden:Int,
    var urlIMG:String,
    var latitud:Double,
    var longitud:Double,
    var costoAlojamiento:Int,
    var costoTransporte:Int,
    var comentario: String
)