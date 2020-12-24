package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION

class JwProviders(private val context: Context) {

    fun available(): Set<JwProvider> {
        val request = Http().path("providers/locale/${context[REGION].first()}")
        val type = object : TypeToken<Set<JwProvider>>() {}

        val results = request.execute(type)
        results.forEach { it.context = context }

        return results
    }
}
