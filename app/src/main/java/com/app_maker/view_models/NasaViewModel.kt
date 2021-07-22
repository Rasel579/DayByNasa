package com.app_maker.view_models

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app_maker.models.NoteData
import com.app_maker.models.Repository
import com.app_maker.models.RepositoryImpl
import java.text.SimpleDateFormat
import java.util.*

class NasaViewModel (
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
        ) : ViewModel(){
    private val DATE = SimpleDateFormat("yyyy-MM-dd").format(Date())
    fun getNotes(){
                liveData.value = AppState.Loading
                Thread{
                  liveData.postValue(AppState.NasaNoteFrgSuccess(repository.getNoteData()))
                }.start()
            }
    fun sendNote(description: String){
        Thread{
            repository.sendData(NoteData(DATE, description, false))
        }.start()
    }


}