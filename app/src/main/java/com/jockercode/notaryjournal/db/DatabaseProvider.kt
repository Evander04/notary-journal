package com.jockercode.notaryjournal.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile private var INSTANCE: NotaryDatabase ? = null

    fun getDatabase(context: Context): NotaryDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NotaryDatabase::class.java,
                "notary_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}