package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken

class Providers(private val region: String) {

    fun available(): Set<Provider> {
        val request = Http().path("providers/locale/${region}")
        val type = object : TypeToken<Set<Provider>>() {}

        return request.execute(type)
    }
}
