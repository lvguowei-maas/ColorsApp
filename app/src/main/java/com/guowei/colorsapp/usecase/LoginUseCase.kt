package com.guowei.colorsapp.usecase

import com.guowei.colorsapp.cache.TokenCache
import com.guowei.colorsapp.networking.ColorsApi
import com.guowei.colorsapp.networking.LoginRequestBody
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val colorsApi: ColorsApi,
    private val tokenCache: TokenCache
) {
    fun isLoggedIn(): Single<Boolean> = Single.fromCallable {
        tokenCache.get() != null
    }

    fun login(username: String, password: String): Completable =
        colorsApi.login(LoginRequestBody(username, password))
            .doOnSuccess {
                tokenCache.save(it.token)
            }
            .ignoreElement()
}
