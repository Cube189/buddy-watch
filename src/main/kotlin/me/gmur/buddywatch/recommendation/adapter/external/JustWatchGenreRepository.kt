package me.gmur.buddywatch.recommendation.adapter.external

import me.gmur.buddywatch.provider.domain.model.Provider
import me.gmur.buddywatch.justwatch.api.Context
import me.gmur.buddywatch.justwatch.api.JwFilterParam.CONTENT_TYPES
import me.gmur.buddywatch.justwatch.api.JwFilterParam.RELEASE_YEAR_FROM
import me.gmur.buddywatch.justwatch.api.JwFilterParam.RELEASE_YEAR_UNTIL
import me.gmur.buddywatch.justwatch.api.JwGenre
import me.gmur.buddywatch.justwatch.api.JwProvider
import me.gmur.buddywatch.justwatch.api.JwProviderCombination
import me.gmur.buddywatch.justwatch.api.JwRegion
import me.gmur.buddywatch.justwatch.api.JwTitle
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre
import me.gmur.buddywatch.recommendation.domain.port.GenreRepository
import org.springframework.stereotype.Service

@Service
class JustWatchGenreRepository : GenreRepository {

    override fun fetchFor(decadeTaste: DecadesTaste, providers: Set<Provider>, region: JwRegion): Set<Genre> {
        val decades = decadeTaste.decades.map { it.toRange() }

        val aggregated = mutableSetOf<JwGenre>()
        for (decade in decades) {
            val titles = fetchTitles(providers, decade)

            val genres = extractGenres(titles, region)

            aggregated.addAll(genres)
        }

        val mapped = aggregated.map { Genre(it.descriptive, it.shorthand) }

        return mapped.toSet()
    }

    private fun fetchTitles(providers: Set<Provider>, decade: IntRange): Set<JwTitle> {
        val titles = toJwProviderCombination(providers).titles()

        return titles.filter().by(
            CONTENT_TYPES to arrayOf("movie"),
            RELEASE_YEAR_FROM to decade.first,
            RELEASE_YEAR_UNTIL to decade.last
        ).second
    }

    private fun extractGenres(titles: Set<JwTitle>, region: JwRegion): List<JwGenre> {
        val genreIds = titles.map { it.details() }
            .mapNotNull { it.genreIds }
            .flatten()
            .toSet()

        return genreIds.mapNotNull { region.genres().get(it) }
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
