package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER

data class JwProvider(
    @SerializedName("clear_name") val name: String,
    @SerializedName("short_name") val shorthand: String
) {

    internal lateinit var context: Context

    fun titles(): JwTitles {
        context[PROVIDER] = shorthand

        return JwTitles(context)
    }
}
