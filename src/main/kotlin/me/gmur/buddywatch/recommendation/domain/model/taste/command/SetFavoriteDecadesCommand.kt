package me.gmur.buddywatch.recommendation.domain.model.taste.command

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.justwatch.api.Region
import me.gmur.buddywatch.recommendation.domain.model.taste.Decade
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste

class SetFavoriteDecadesCommand(
    val decades: Set<Int>,
    val region: Region,
    val token: Token
) {

    fun toDecadesTaste(): DecadesTaste {
        val mapped = decades.map { Decade(it) }.toSet()

        return DecadesTaste(mapped)
    }
}
