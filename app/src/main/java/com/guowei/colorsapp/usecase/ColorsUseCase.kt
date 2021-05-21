package com.guowei.colorsapp.usecase

import com.guowei.colorsapp.cache.SessionCache
import com.guowei.colorsapp.networking.api.StorageApi
import io.reactivex.Single
import javax.inject.Inject

class ColorsUseCase @Inject constructor(
    private val storageApi: StorageApi,
    private val sessionCache: SessionCache
) {
    /**
     * Get current color.
     * If storage id exists, get color by storage id from server
     * Otherwise, create storage with color WHITE and then save the storage id in session cache
     */
    fun getCurrent(): Single<String> =
        Single.fromCallable {
            sessionCache.getStorageId().orEmpty()
        }
            .flatMap { storageId ->
                if (storageId.isBlank()) {
                    storageApi.create(WHITE)
                        .doOnSuccess { sessionCache.saveStorageId(it.id) }
                        .map { it.data }
                } else {
                    storageApi.get(storageId).map { it.data }
                }
            }


    companion object {
        private const val WHITE = "#000000"
    }

}