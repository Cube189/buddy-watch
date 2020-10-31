package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER

data class Provider(
    @SerializedName("clear_name") private val name: String,
    @SerializedName("short_name") private val shorthand: String
) {

    internal lateinit var context: Context

    init {
        if (!::context.isInitialized) context = Context()
        context[PROVIDER] = shorthand
    }

    fun titles(): Titles {
        return Titles(context)
    }
}