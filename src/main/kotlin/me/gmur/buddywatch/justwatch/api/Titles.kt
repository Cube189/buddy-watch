package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION
import me.gmur.buddywatch.justwatch.api.FilterParam.CONTENT_TYPES
import me.gmur.buddywatch.justwatch.api.FilterParam.PROVIDERS

class Titles(private val context: Context) {

    private val path = "titles/${context[REGION]}/popular"

    fun all(): Pair<Int, Set<Title>> {
        val request = Http().path(path).body(
            mapOf(
                PROVIDERS to arrayOf(context[PROVIDER])
            )
        )
        val type = object : TypeToken<Set<Title>>() {}

        val result = request.executeWithResultCount(type)
        result.second.forEach { it.context = context }

        return result
    }

    fun movies(): Pair<Int, Set<Movie>> {
        val request = Http().path(path).body(
            mapOf(
                PROVIDERS to arrayOf(context[PROVIDER]),
                CONTENT_TYPES to arrayOf("movie")
            )
        )
        val type = object : TypeToken<Set<Movie>>() {}

        return request.executeWithResultCount(type)
    }

    fun shows(): Pair<Int, Set<Show>> {
        val request = Http().path(path).body(
            mapOf(
                PROVIDERS to arrayOf(context[PROVIDER]),
                CONTENT_TYPES to arrayOf("show")
            )
        )
        val type = object : TypeToken<Set<Show>>() {}

        return request.executeWithResultCount(type)
    }

    fun filter(): Filter {
        return Filter(context)
    }
}
