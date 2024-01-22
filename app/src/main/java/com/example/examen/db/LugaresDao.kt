package com.example.examen.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LugaresDao {

    @Query ("SELECT * FROM lugares ORDER BY id")
    fun findAll():List<Lugares>

    @Insert
    fun insertar(lugares:Lugares):Long

    @Update
    fun actualizar(lugares: Lugares)

    @Delete
    fun eliminar(lugares: Lugares)
}