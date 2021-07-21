package com.app_maker.models

import com.app_maker.models.rest.MarsMutableApiDTO
import com.app_maker.models.rest.NasaEpicDTO
import com.app_maker.models.rest.NasaPictureDTO
import retrofit2.Callback

interface Repository {
    fun getDataFromApi(callback : Callback<NasaPictureDTO>)
    fun getDataFromApiPrevDatePic(date : String, callback : Callback<NasaPictureDTO>)
    fun getDataFromEPICNasaApi(date: String, callback: Callback<MutableList<NasaEpicDTO>>)
    fun getDataFromMarsApi(callback: Callback<MarsMutableApiDTO>)
    fun getNoteData() : List<NoteData>
    fun sendData(notes : NoteData)
    fun getFakeDataNotes() : MutableList<NoteData>
}