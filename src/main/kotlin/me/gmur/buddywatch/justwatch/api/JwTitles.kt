package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION
import me.gmur.buddywatch.justwatch.api.JwFilterParam.CONTENT_TYPES
import me.gmur.buddywatch.justwatch.api.JwFilterParam.PROVIDERS

class JwTitles(private val context: Context) {

    private val path = "titles/${context[REGION].first()}/popular"

    fun all(): Pair<Int, Set<JwTitle>> {
        val request = Http().path(path).body(
            mapOf(
                PROVIDERS to context[PROVIDER]
            )
        )
        val type = object : TypeToken<Set<JwTitle>>() {}

        val result = request.executeWithResultCount(type)
        result.second.forEach { it.context = context }

        return result
    }

    fun movies(): Pair<Int, Set<Movie>> {
        val request = Http().path(path).body(
            mapOf(
                PROVIDERS to context[PROVIDER],
                CONTENT_TYPES to arrayOf("movie")
            )
        )
        val type = object : TypeToken<Set<Movie>>() {}

        return request.executeWithResultCount(type)
    }

    fun shows(): Pair<Int, Set<Show>> {
        val request = Http().path(path).body(
            mapOf(
                PROVIDERS to context[PROVIDER],
                CONTENT_TYPES to arrayOf("show")
            )
        )
        val type = object : TypeToken<Set<Show>>() {}

        return request.executeWithResultCount(type)
    }

    fun filter(): JwFilter {
        return JwFilter(context)
    }
}
