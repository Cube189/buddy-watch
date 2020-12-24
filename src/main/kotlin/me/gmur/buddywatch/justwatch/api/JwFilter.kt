package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION
import me.gmur.buddywatch.justwatch.api.JwFilterParam.PROVIDERS

class JwFilter(private val context: Context) {

    private val path = "titles/${context[REGION].first()}/popular"

    fun by(vararg params: Pair<JwFilterParam, Any>): Pair<Int, Set<JwTitle>> {
        return by(params.toMap())
    }

    fun by(params: Map<JwFilterParam, Any>): Pair<Int, Set<JwTitle>> {
        val paramsWithProvider = params + mapOf(PROVIDERS to context[PROVIDER])

        val request = Http().path(path).body(paramsWithProvider)
        val type = object : TypeToken<Set<JwTitle>>() {}

        val result = request.executeWithResultCount(type)
        result.second.forEach { it.context = context }

        return result
    }
}
