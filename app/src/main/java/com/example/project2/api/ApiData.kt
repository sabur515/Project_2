package com.example.project2.api

import androidx.room.ColumnInfo
import com.squareup.moshi.Json

data class ApiData(

    @Json(name = "message")
    @ColumnInfo(name = "image_url")
    val message: String,

    @Json(name = "status")
    @ColumnInfo(name = "status")
    val status: String

)
