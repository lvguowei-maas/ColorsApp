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
                    storageApi.create(StorageRequestBody(YellowGreen))
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

    fun update(color: String): Single<String> =
        Single.fromCallable {
            sessionCache.getStorageId()
        }
            .flatMap { id ->
                storageApi.update(id, StorageRequestBody(color)).map { it.data }
            }
            .retryWhen {
                it.take(3).delay(1, TimeUnit.SECONDS)
            }

    fun getColorSet(): Single<List<String>> =
        Single.just(
            listOf(
                YellowGreen,
                Tomato,
                RoyalBlue,
                Plum,
                Orange,
                Moccasin,
                MistyRose,
                LightSteelBlue,
                LightSalmon,
                LightCyan
            )
        )


    companion object {
        private const val YellowGreen = "#9ACD32"
        private const val Tomato = "#FF6347"
        private const val RoyalBlue = "#4169E1"
        private const val Plum = "#DDA0DD"
        private const val Orange = "#FFA500"
        private const val Moccasin = "#FFE4B5"
        private const val MistyRose = "#FFE4E1"
        private const val LightSteelBlue = "#B0C4DE"
        private const val LightSalmon = "#FFA07A"
        private const val LightCyan = "#E0FFFF"

    }
}