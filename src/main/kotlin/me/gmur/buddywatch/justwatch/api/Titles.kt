package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION

class Titles(private val context: Context) {

    fun all(): Pair<Int, Set<Title>> {
        val request = Http().path("titles/${context[REGION]}/popular")
            .body("{\"providers\":[\"${context[PROVIDER]}\"]}")
        val type = object : TypeToken<Set<Title>>() {}

        return request.executeWithResultCount(type)
    }
}
