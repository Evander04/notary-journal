package com.jockercode.notaryjournal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jockercode.notaryjournal.model.Notary

@Dao
interface NotaryDao {
    @Insert
    suspend fun insert(record: Notary)

    @Query("SELECT * FROM Notary_Record")
    suspend fun getAll():List<Notary>
}