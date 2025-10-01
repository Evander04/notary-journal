package com.jockercode.notaryjournal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jockercode.notaryjournal.dao.NotaryDao
import com.jockercode.notaryjournal.model.Notary

@Database(entities = [Notary::class], version = 2)
abstract class NotaryDatabase : RoomDatabase(){
    abstract fun notaryDao(): NotaryDao
}