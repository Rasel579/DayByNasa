package com.app_maker.view_models

import com.app_maker.models.rest.MarsMutableApiDTO
import com.app_maker.models.rest.NasaEpicDTO
import com.app_maker.models.rest.NasaPictureDTO

sealed class AppState{
    data class Success(val dataFromNasa : NasaPictureDTO) : AppState()
    data class EarthFrgSuccess(val dataFromNasa : MutableList<NasaEpicDTO>?) : AppState()
    data class MarsFrgSuccess(val dataFromNasa : MarsMutableApiDTO?) : AppState()
    object Loading : AppState()
    data class Errors(val error: Throwable) : AppState()
}
