package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken

class JwRegions {

    companion object {
        fun available(): Set<JwRegion> {
            val request = Http().path("locales/state")
            val type = object : TypeToken<Set<JwRegion>>() {}

            return request.execute(type)
        }
    }
}
