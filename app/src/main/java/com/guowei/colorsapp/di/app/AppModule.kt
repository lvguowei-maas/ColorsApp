package com.guowei.colorsapp.di.app

import android.app.Application
import com.guowei.colorsapp.networking.ColorsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class AppModule(val application: Application) {

    @Provides
    @AppScope
    fun retrofit1(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("TODO")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun application() = application

    @Provides
    @AppScope
    fun colorsApi(retrofit: Retrofit) = retrofit.create(ColorsApi::class.java)

}