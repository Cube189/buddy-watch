package me.gmur.buddywatch.justwatch.api

import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION

class Titles(private val context: Context) {

    private val path = "titles/${context[REGION]}/popular"

    fun all(): Pair<Int, Set<Title>> {
        val request = Http().path(path).body(
            mapOf("providers" to arrayOf(context[PROVIDER]))
        )
        val type = object : TypeToken<Set<Title>>() {}

        return request.executeWithResultCount(type)
    }

    fun movies(): Pair<Int, Set<Movie>> {
        val request = Http().path(path).body(
            mapOf(
                "providers" to arrayOf(context[PROVIDER]),
                "content_types" to arrayOf("movie")
            )
        )
        val type = object : TypeToken<Set<Movie>>() {}

        return request.executeWithResultCount(type)
    }

    fun shows(): Pair<Int, Set<Show>> {
        val request = Http().path(path).body(
            mapOf(
                "providers" to arrayOf(context[PROVIDER]),
                "content_types" to arrayOf("show")
            )
        )
        val type = object : TypeToken<Set<Show>>() {}

        return request.executeWithResultCount(type)
    }
}
