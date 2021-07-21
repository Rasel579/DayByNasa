package com.app_maker.models

import android.annotation.SuppressLint
import com.app_maker.BuildConfig
import com.app_maker.models.databases.NasaNoteDatabase
import com.app_maker.models.databases.NasaNoteEntity
import com.app_maker.models.rest.*
import com.app_maker.nasaFakeNotes
import retrofit2.Callback

class RepositoryImpl : Repository {
    override fun getDataFromApi(callback: Callback<NasaPictureDTO>) {
         BackendRepo.api.getDataFromNasa(BuildConfig.NASA_APIKEY).enqueue(callback)
    }

    @SuppressLint("SimpleDateFormat")
    override fun getDataFromApiPrevDatePic(date: String, callback: Callback<NasaPictureDTO>) {
        BackendRepo.api.getDataFromNasaPreviousDate(date, BuildConfig.NASA_APIKEY).enqueue(callback)
    }

    override fun getDataFromEPICNasaApi(
        date: String,
        callback: Callback<MutableList<NasaEpicDTO>>
    ) {
        BackendRepo.api.getDataFromNasaEpicApi(date, BuildConfig.NASA_APIKEY).enqueue(callback)
    }

    override fun getDataFromMarsApi(callback: Callback<MarsMutableApiDTO>) {
        BackendRepo.api.getDataFromMarsApi(800, BuildConfig.NASA_APIKEY).enqueue(callback)
    }

    override fun getNoteData(): List<NoteData> = convertEntityToObj(
        NasaNoteDatabase.db.nasaNoteDAO().getAll()
    )

    private fun convertEntityToObj(entity: MutableList<NasaNoteEntity>): List<NoteData> =
        entity.map {
             NoteData(it.date, it.description)
        }



    override fun sendData(notes: NoteData){
          NasaNoteDatabase.db.nasaNoteDAO().saveEntity(convertNoteDataToEntity(notes))
    }

    private fun convertNoteDataToEntity(notes: NoteData): NasaNoteEntity =
       NasaNoteEntity(notes.date, notes.description)


    override fun getFakeDataNotes() = nasaFakeNotes

}