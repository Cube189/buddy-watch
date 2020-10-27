package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("full_locale") private val id: String,
    @SerializedName("iso_3166_2") private val iso: String,
    @SerializedName("country") private val country: String
) {

    fun providers(): Providers {
        return Providers(id)
    }
}
