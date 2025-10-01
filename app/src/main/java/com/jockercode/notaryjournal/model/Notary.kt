package com.jockercode.notaryjournal.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Notary_Record")
data class Notary (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val fullName: String?,
    val address: String?,
    val dob: String?,
    val documentType: String?,
    val notaryType: String?,
    val dateCreated: String
)