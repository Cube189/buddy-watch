package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName

data class Provider(
    @SerializedName("clear_name") private val name: String,
    @SerializedName("short_name") private val shorthand: String
)
