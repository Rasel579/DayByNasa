package com.app_maker.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app_maker.models.Repository
import com.app_maker.models.RepositoryImpl
import com.app_maker.models.rest.NasaPictureDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
   val liveData: MutableLiveData<Any> = MutableLiveData(),
   val repository: Repository = RepositoryImpl()
) : ViewModel() {
     fun loadDataFromApi(){
       liveData.value = AppState.Loading
       val callbackFromRetrofit = object : Callback<NasaPictureDTO> {
           override fun onResponse(call: Call<NasaPictureDTO>, response: Response<NasaPictureDTO>) {
               if (response.isSuccessful){
                   liveData.postValue(
                       response.body()?.let { AppState.Success(it) }
                   )
               }
           }
           override fun onFailure(call: Call<NasaPictureDTO>, t: Throwable) {
               t.printStackTrace()
           }
       }
       repository.getDataFromApi(callbackFromRetrofit)
     }
}