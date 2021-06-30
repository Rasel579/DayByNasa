package com.app_maker.models.rest

import kotlinx.android.parcel.Parcelize

@Parcelize
data class NasaPictureDTO(
    val date : String,
    val explanation : String,
    val mediaType : String,
    val title : String,
    val urlPicture: String
)
