package com.guowei.colorsapp.usecase

import com.guowei.colorsapp.networking.ColorsApi
import com.guowei.colorsapp.networking.LoginRequestBody
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val colorsApi: ColorsApi
) {
    fun isLoggedIn(): Single<Boolean> = Single.just(false)

    fun login(username: String, password: String) =
        colorsApi.login(LoginRequestBody(username, password))
            .map {
                it.token
            }
}
