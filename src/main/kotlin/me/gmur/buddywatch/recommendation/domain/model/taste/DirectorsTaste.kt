package me.gmur.buddywatch.recommendation.domain.model.taste

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id

class DirectorsTaste(
    val values: Set<Director>,
    override val id: DirectorsTasteId = DirectorsTasteId.New
) : Entity<DirectorsTasteId>()

sealed class DirectorsTasteId : Id<Long>() {
    object New : DirectorsTasteId()
    data class Persisted(override val value: Long) : DirectorsTasteId()
}

data class Director(val name: String)
