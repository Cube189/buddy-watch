package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER

data class Provider(
    @SerializedName("clear_name") val name: String,
    @SerializedName("short_name") val shorthand: String
) {

    internal lateinit var context: Context

    fun titles(): Titles {
        context[PROVIDER] = shorthand

        return Titles(context)
    }
}
