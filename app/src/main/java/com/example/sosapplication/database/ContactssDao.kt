package com.example.sosapplication.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactssDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact:EmergencyContactTable)

    @Update
    suspend fun update(contact:EmergencyContactTable)

    @Delete
    suspend fun delete(contact:EmergencyContactTable)

    @Query("SELECT * from contacts ORDER BY name ASC")
    fun getItems(): Flow<List<EmergencyContactTable>>
}