package me.gmur.buddywatch.recommendation.domain.model.taste

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id

class GenresTaste(
    val values: Set<Genre>,
    override val id: GenresTasteId = GenresTasteId.New
) : Entity<GenresTasteId>()

sealed class GenresTasteId : Id<Long>() {
    object New : GenresTasteId()
    data class Persisted(override val value: Long) : GenresTasteId()
}

data class Genre(val value: String)
