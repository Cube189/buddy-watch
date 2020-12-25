package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION
import me.gmur.buddywatch.justwatch.api.JwTitleType.MOVIE
import me.gmur.buddywatch.justwatch.api.JwTitleType.SHOW

open class JwTitle(
    val id: Long,
    val title: String,
    @SerializedName("object_type") val type: JwTitleType
) {

    internal lateinit var context: Context

    fun details(): JwTitleDetails {
        val request = Http().path("titles/${type.toString().toLowerCase()}/$id/locale/${context[REGION].first()}")
        val type = object : TypeToken<JwTitleDetails>() {}

        return request.execute(type)
    }
}

enum class JwTitleType {

    @SerializedName("movie")
    MOVIE,

    @SerializedName("show")
    SHOW
}

class JwMovie(id: Long, title: String) : JwTitle(id, title, MOVIE)

class JwShow(id: Long, title: String) : JwTitle(id, title, SHOW)
