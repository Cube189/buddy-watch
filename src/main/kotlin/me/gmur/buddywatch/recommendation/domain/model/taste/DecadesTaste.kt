package me.gmur.buddywatch.recommendation.domain.model.taste

import me.gmur.buddywatch.common.domain.model.Entity
import me.gmur.buddywatch.common.domain.model.Id

class DecadesTaste(
    val values: Set<Decade>,
    override val id: DecadesTasteId = DecadesTasteId.New
) : Entity<DecadesTasteId>()

sealed class DecadesTasteId : Id<Long>() {
    object New : DecadesTasteId()
    data class Persisted(override val value: Long) : DecadesTasteId()
}

data class Decade(val value: String)
