package com.app_maker.models.databases

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app_maker.App

@Database(entities = [NasaNoteEntity::class],version = 2, exportSchema = false )
abstract  class NasaNoteDatabase : RoomDatabase() {
 abstract fun nasaNoteDAO() : NoteDAO
  companion object{
      private val  DB_NAME = "day_by_by_nasa.db"
      val db :NasaNoteDatabase by lazy {
         Room.databaseBuilder(
             App.appInstance,
             NasaNoteDatabase::class.java,
             DB_NAME
         ).build()
      }
  }
}