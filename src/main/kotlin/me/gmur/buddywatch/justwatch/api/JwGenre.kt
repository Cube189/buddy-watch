package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName

data class JwGenre(
    val id: Int,
    @SerializedName("short_name") val shorthand: String,
    @SerializedName("technical_name") val technicalName: String,
    @SerializedName("translation") val descriptive: String,
    @SerializedName("slug") val slug: String
)
