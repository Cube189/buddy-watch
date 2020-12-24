package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION
import me.gmur.buddywatch.justwatch.api.TitleType.MOVIE
import me.gmur.buddywatch.justwatch.api.TitleType.SHOW

open class JwTitle(
    val id: Long,
    val title: String,
    @SerializedName("object_type") val type: TitleType
) {

    internal lateinit var context: Context

    fun details(): JwTitleDetails {
        val request = Http().path("titles/${type.toString().toLowerCase()}/$id/locale/${context[REGION].first()}")
        val type = object : TypeToken<JwTitleDetails>() {}

        return request.execute(type)
    }
}

enum class TitleType {

    @SerializedName("movie")
    MOVIE,

    @SerializedName("show")
    SHOW
}

class Movie(id: Long, title: String) : JwTitle(id, title, MOVIE)

class Show(id: Long, title: String) : JwTitle(id, title, SHOW)
