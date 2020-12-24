package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION

data class JwRegion(
    @SerializedName("full_locale") val id: String,
    @SerializedName("iso_3166_2") val iso: String,
    @SerializedName("country") val country: String
) {

    private val context = Context()

    init {
        context[REGION] = id
    }

    fun genres(): JwGenres {
        return JwGenres(context)
    }

    fun providers(): JwProviders {
        return JwProviders(context)
    }
}
