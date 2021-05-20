package com.guowei.colorsapp.cache

import javax.inject.Inject

class InMemoryTokenCache @Inject constructor() : TokenCache {

    private var token: String? = null

    override fun save(token: String) {
        this.token = token
    }

    override fun get() = token
}