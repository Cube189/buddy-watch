package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken

class Regions {

    companion object {
        fun available(): Set<Region> {
            val request = Http().path("locales/state")
            val type = object : TypeToken<Set<Region>>() {}

            return request.execute(type)
        }
    }
}
