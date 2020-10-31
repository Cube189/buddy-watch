package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION

class Providers(private val context: Context) {

    fun available(): Set<Provider> {
        val request = Http().path("providers/locale/${context[REGION]}")
        val type = object : TypeToken<Set<Provider>>() {}

        val results = request.execute(type)
        results.forEach { it.context = context }

        return results
    }
}
