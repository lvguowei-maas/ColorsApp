package com.guowei.colorsapp.networking

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ColorsApi {

    @POST("v1/login")
    fun login(@Body body: LoginRequestBody): Single<LoginResponse>
}