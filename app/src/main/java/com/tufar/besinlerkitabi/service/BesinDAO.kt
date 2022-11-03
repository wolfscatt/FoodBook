package com.tufar.besinlerkitabi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tufar.besinlerkitabi.model.Besin

@Dao
interface BesinDAO {

    // Data Access Object - Veri Erişim Objesi

    @Insert
    suspend fun insertAll(vararg besin : Besin) : List<Long>

    // Insert -> Room, insert into
    // suspend -> coroutine scope
    // vararg -> birden fazla istediğimiz sayıda argüman verebilmemizi sağlıyor.
    // List<Long> -> uzun uuid lerimiz olduğu için long

    @Query("SELECT * FROM besin")
    suspend fun getAllBesin() : List<Besin>

    @Query("Select * From besin Where uuid = :besinId")
    suspend fun getBesin(besinId : Int) : Besin

    @Query("Delete from besin")
    suspend fun deleteAllBesin()

}