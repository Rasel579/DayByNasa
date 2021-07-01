package com.app_maker.models.rest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NasaPictureDTO(
    val date : String,
    val explanation : String,
    val mediaType : String,
    val title : String,
    @SerializedName("url")
    val urlPicture: String
) : Parcelable
