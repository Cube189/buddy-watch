package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName

data class JwCastMember(
    @SerializedName("person_id") val id: Long,
    val role: JwRole,
    val name: String,
    @SerializedName("character_name") val playedAs: String
) {

    val nameIndexed get() = name.capitalize().replace(" ", "")
}

enum class JwRole {
    ACTOR,
    ASSISTANT_DIRECTOR,
    AUTHOR,
    CO_EXECUTIVE_PRODUCER,
    CO_PRODUCER,
    DIRECTOR,
    EDITOR,
    EXECUTIVE_PRODUCER,
    MUSIC,
    ORIGINAL_MUSIC_COMPOSER,
    PRODUCER,
    PRODUCTION_DESIGN,
    SCREENPLAY,
    VISUAL_EFFECTS,
    WRITER,
}
