package me.gmur.buddywatch.recommendation.domain.model.taste

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.common.domain.model.Aggregate

class Taste(
    val token: Token,
    val actors: ActorsTaste,
    val decades: DecadesTaste,
    val directors: DirectorsTaste,
    val genres: GenresTaste,
) : Aggregate
