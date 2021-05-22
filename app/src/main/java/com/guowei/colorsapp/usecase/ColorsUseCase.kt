package com.guowei.colorsapp.usecase

import com.guowei.colorsapp.cache.SessionCache
import com.guowei.colorsapp.networking.api.StorageApi
import com.guowei.colorsapp.networking.schema.StorageRequestBody
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ColorsUseCase @Inject constructor(
    private val storageApi: StorageApi,
    private val sessionCache: SessionCache
) {

    /**
     * Get the current color from server if exists, otherwise create with color white.
     * If storage id exists, get color by storage id from server.
     * Otherwise, create storage with color WHITE and then save the storage id in session cache.
     */
    fun getOrCreate(): Single<String> =
        Single.fromCallable {
            sessionCache.getStorageId().orEmpty()
        }
            .flatMap { storageId ->
                if (storageId.isBlank()) {
                    storageApi.create(StorageRequestBody(White))
                        .doOnSuccess { sessionCache.saveStorageId(it.id) }
                        .map { it.data }
                } else {
                    storageApi.get(storageId).map { it.data }
                }
            }
            .retryWhen {
                // retry max 3 times with 1 sec delay
                it.take(3).delay(1, TimeUnit.SECONDS)
            }

    fun getColorSet(): Single<List<String>> = Single.just(listOf(White, AliceBlue, Aqua, DarkBlue))


    companion object {
        private const val White = "#ffffff"
        private const val AliceBlue = "#F0F8FF"
        private const val Aqua = "#00FFFF"
        private const val DarkBlue = "#00008B"

    }
}