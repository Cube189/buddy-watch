package me.gmur.buddywatch.justwatch.api

import com.google.gson.annotations.SerializedName

class JwTitleDetails(
    val id: Long,
    val title: String,
    @SerializedName("short_description") val description: String,
    @SerializedName("original_release_year") val released: Int,
    @SerializedName("credits") val cast: Set<JwCastMember>,
    @SerializedName("genre_ids") val genreIds: Set<Int>
)
