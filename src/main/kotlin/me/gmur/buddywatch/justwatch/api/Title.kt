package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName

class Title(
    private val id: Long,
    private val title: String,
    @SerializedName("object_type") private val type: TitleType
)

enum class TitleType {

    @SerializedName("movie")
    MOVIE,

    @SerializedName("show")
    SHOW
}
