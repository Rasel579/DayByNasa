package com.app_maker.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app_maker.models.Repository
import com.app_maker.models.RepositoryImpl
import com.app_maker.models.rest.NasaEpicDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    var liveData: MutableLiveData<Any> = MutableLiveData(),
    private val repository : Repository = RepositoryImpl()
) : ViewModel() {
    fun loadDataFromApi(date : String){
        liveData.value = AppState.Loading
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
        repository.getDataFromEPICNasaApi(date, callBackFromEpicApi)
    }

}