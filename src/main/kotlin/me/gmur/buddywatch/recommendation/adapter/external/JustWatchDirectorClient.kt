package me.gmur.buddywatch.recommendation.adapter.external

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.justwatch.api.Context
import me.gmur.buddywatch.justwatch.api.JwFilterParam.CONTENT_TYPES
import me.gmur.buddywatch.justwatch.api.JwFilterParam.GENRES
import me.gmur.buddywatch.justwatch.api.JwFilterParam.RELEASE_YEAR_FROM
import me.gmur.buddywatch.justwatch.api.JwFilterParam.RELEASE_YEAR_UNTIL
import me.gmur.buddywatch.justwatch.api.JwProvider
import me.gmur.buddywatch.justwatch.api.JwProviderCombination
import me.gmur.buddywatch.justwatch.api.JwRole
import me.gmur.buddywatch.justwatch.api.JwTitle
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste
import me.gmur.buddywatch.recommendation.domain.port.DirectorClient
import org.springframework.stereotype.Component

@Component
class JustWatchDirectorClient : DirectorClient {

    override fun fetchFor(
        decadesTaste: DecadesTaste,
        genresTaste: GenresTaste,
        group: Group
    ): Set<Director> {
        val decades = decadesTaste.decades.map { it.toRange() }
        val genres = genresTaste.genres
        val providers = group.providers

        val aggregated = mutableSetOf<Director>()
        for (decade in decades) {
            val titles = fetchTitles(providers, decade, genres)

            val actors = extractDirectors(titles)

            aggregated.addAll(actors)
        }

        return aggregated.toSet()
    }

    private fun fetchTitles(providers: Set<Provider>, decade: IntRange, genres: Set<Genre>): Set<JwTitle> {
        val titles = toJwProviderCombination(providers).titles()

        return titles.filter().by(
            CONTENT_TYPES to arrayOf("movies"),
            RELEASE_YEAR_FROM to decade.first,
            RELEASE_YEAR_UNTIL to decade.last,
            GENRES to genres.map { it.shorthand }
        ).second
    }

    private fun extractDirectors(titles: Set<JwTitle>): List<Director> {
        val cast = titles.map { it.details() }
            .map { it.cast }
            .flatten()
            .toSet()

        return cast.filter { it.role == JwRole.DIRECTOR }
            .map { Director(it.name, it.id) }
    }

    private fun toJwProviderCombination(providers: Set<Provider>): JwProviderCombination {
        val context = Context()
        context[Context.Key.REGION] = "en_US"

        val jwProviders = providers.map { JwProvider(it.name, it.shorthand) }

        val combination = JwProviderCombination(jwProviders)
        combination.context = context

        return combination
    }
}
