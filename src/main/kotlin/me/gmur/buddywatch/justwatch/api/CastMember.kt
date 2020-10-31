package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName

data class CastMember(
    @SerializedName("person_id") val id: Long,
    val role: Role,
    val name: String,
    @SerializedName("character_name") val playedAs: String
) {

    val nameIndexed get() = name.capitalize().replace(" ", "")
}

enum class Role {
    ACTOR,
}
