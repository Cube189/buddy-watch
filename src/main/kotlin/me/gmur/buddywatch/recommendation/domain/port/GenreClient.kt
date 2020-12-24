package me.gmur.buddywatch.recommendation.domain.port

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.justwatch.api.JwGenre
import me.gmur.buddywatch.justwatch.api.JwRegion
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste

interface GenreClient {

    fun fetchFor(decadeTaste: DecadesTaste, group: Group, region: JwRegion): Set<JwGenre>
}
