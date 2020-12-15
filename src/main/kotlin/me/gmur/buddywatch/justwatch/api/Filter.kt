package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION
import me.gmur.buddywatch.justwatch.api.FilterParam.PROVIDERS

class Filter(private val context: Context) {

    private val path = "titles/${context[REGION]}/popular"

    fun by(params: Map<FilterParam, Array<String>>): Pair<Int, Set<Title>> {
        val paramsWithProvider = params + mapOf(PROVIDERS to arrayOf(context[Context.Key.PROVIDER]))

        val request = Http().path(path).body(paramsWithProvider)
        val type = object : TypeToken<Set<Title>>() {}

        val result = request.executeWithResultCount(type)
        result.second.forEach { it.context = context }

        return result
    }
}
