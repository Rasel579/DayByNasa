package com.app_maker.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app_maker.models.Repository
import com.app_maker.models.RepositoryImpl
import com.app_maker.models.rest.MarsMutableApiDTO
import com.app_maker.models.rest.NasaEpicDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPagerViewModel (
    val liveData: MutableLiveData<Any> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
 ) : ViewModel(){
     fun loadDataFromMars(date : String){
         liveData.value = AppState.Loading
         val callbackFromMarsApi = object : Callback<MarsMutableApiDTO>{
             override fun onResponse(
                 call: Call<MarsMutableApiDTO>,
                 response: Response<MarsMutableApiDTO>
             ) {
                 if (response.isSuccessful && response.body() != null){
                     liveData.postValue(
                         AppState.MarsFrgSuccess(response.body())
                     )
                 }
             }

             override fun onFailure(call: Call<MarsMutableApiDTO>, t: Throwable) {
                 t.printStackTrace()
             }
         }
         val callBackFromEpicApi = object : Callback<MutableList<NasaEpicDTO>>{
             override fun onResponse(
                 call: Call<MutableList<NasaEpicDTO>>,
                 response: Response<MutableList<NasaEpicDTO>>
             ) {
                 if (response.isSuccessful && !response.body().isNullOrEmpty()){
                     liveData.postValue(
                         AppState.EarthFrgSuccess(response.body())
                     )
                 }
             }

             override fun onFailure(call: Call<MutableList<NasaEpicDTO>>, t: Throwable) {
                 t.printStackTrace()
             }
         }
         repository.getDataFromMarsApi(callbackFromMarsApi)
         repository.getDataFromEPICNasaApi(date, callBackFromEpicApi)

     }

}