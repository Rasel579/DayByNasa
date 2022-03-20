package com.app_maker.models.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDAO {
    @Query("Select * from NasaNoteEntity")
    fun getAll(): MutableList<NasaNoteEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveEntity(entity: NasaNoteEntity)
}