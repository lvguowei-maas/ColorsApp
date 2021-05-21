package com.guowei.colorsapp.usecase

import com.guowei.colorsapp.cache.SessionCache
import com.guowei.colorsapp.networking.api.UserApi
import com.guowei.colorsapp.networking.schema.LoginRequestBody
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userApi: UserApi,
    private val sessionCache: SessionCache
) {
    fun isLoggedIn(): Single<Boolean> = Single.fromCallable {
        sessionCache.getToken() != null
    }

    fun login(username: String, password: String): Completable =
        userApi.login(LoginRequestBody(username, password))
            .doOnSuccess {
                sessionCache.saveToken(it.token)
            }
            .ignoreElement()
}
