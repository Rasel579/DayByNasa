package com.app_maker.models.databases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NasaNoteEntity(
    @PrimaryKey
    val date : String,
    val description : String
)
