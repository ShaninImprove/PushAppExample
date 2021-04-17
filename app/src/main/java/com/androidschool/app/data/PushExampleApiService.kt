package com.androidschool.app.data

import com.androidschool.app.data.model.DeviceIdApiModel
import io.reactivex.rxjava3.core.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface PushExampleApiService {

    @POST("add_device_id")
    fun addDeviceId(@Body device: DeviceIdApiModel): Completable
}