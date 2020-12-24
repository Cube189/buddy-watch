package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.justwatch.api.Genre
import me.gmur.buddywatch.justwatch.api.Region
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste

interface GenreClient {

    fun fetchFor(decadeTaste: DecadesTaste, group: Group, region: Region): Set<Genre>
}
