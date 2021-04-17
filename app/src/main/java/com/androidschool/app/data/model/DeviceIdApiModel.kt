package com.androidschool.app.data.model

import com.google.gson.annotations.SerializedName

data class DeviceIdApiModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("deviceId")
    val deviceId: String
)
