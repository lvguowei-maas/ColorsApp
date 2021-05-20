package com.guowei.colorsapp.di.app

import com.guowei.colorsapp.cache.TokenCache
import com.guowei.colorsapp.cache.InMemoryTokenCache
import dagger.Binds
import dagger.Module

@Module
abstract class CacheModule {

    @AppScope
    @Binds
    abstract fun tokenCache(inMemoryTokenCache: InMemoryTokenCache): TokenCache
}
