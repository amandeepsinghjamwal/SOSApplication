package com.example.sosapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class EmergencyContactTable (
@PrimaryKey(autoGenerate = true)
val id: Int? = 0,
@ColumnInfo(name = "name")
val name: String,
@ColumnInfo(name = "number")
val number: String
)