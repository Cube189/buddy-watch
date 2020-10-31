package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER

data class Provider(
    @SerializedName("clear_name") private val name: String,
    @SerializedName("short_name") private val shorthand: String
) {

    internal lateinit var context: Context

    fun titles(): Titles {
        context[PROVIDER] = shorthand

        return Titles(context)
    }
}
