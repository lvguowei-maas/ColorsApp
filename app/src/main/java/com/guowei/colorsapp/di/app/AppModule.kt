package com.guowei.colorsapp.di.app

import android.app.Application
import com.guowei.colorsapp.networking.ColorsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class AppModule(private val application: Application) {

    @Provides
    @AppScope
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://54t9f06ot1.execute-api.eu-central-1.amazonaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun application() = application

    @Provides
    @AppScope
    fun colorsApi(retrofit: Retrofit): ColorsApi = retrofit.create(ColorsApi::class.java)
}