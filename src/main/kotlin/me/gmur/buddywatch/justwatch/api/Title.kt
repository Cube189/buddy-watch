package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName
import me.gmur.buddywatch.justwatch.api.TitleType.MOVIE
import me.gmur.buddywatch.justwatch.api.TitleType.SHOW

open class Title(
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

class Movie(id: Long, title: String) : Title(id, title, MOVIE)

class Show(id: Long, title: String) : Title(id, title, SHOW)
