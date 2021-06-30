package com.app_maker.view_models

import com.app_maker.models.rest.NasaPictureDTO

sealed class AppState{
    data class Success(val dataFromNasa : NasaPictureDTO) : AppState()
    object Loading : AppState()
    data class Errors(val error: Throwable) : AppState()
}
