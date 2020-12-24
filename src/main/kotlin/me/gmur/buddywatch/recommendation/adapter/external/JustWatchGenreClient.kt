package me.gmur.buddywatch.recommendation.adapter.external

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.justwatch.api.Context
import me.gmur.buddywatch.justwatch.api.FilterParam.RELEASE_YEAR_FROM
import me.gmur.buddywatch.justwatch.api.FilterParam.RELEASE_YEAR_UNTIL
import me.gmur.buddywatch.justwatch.api.Genre
import me.gmur.buddywatch.justwatch.api.Region
import me.gmur.buddywatch.justwatch.api.Title
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.port.GenreClient
import org.springframework.stereotype.Component
import me.gmur.buddywatch.justwatch.api.Provider as JwProvider

@Component
class JustWatchGenreClient : GenreClient {

    override fun fetchFor(decadeTaste: DecadesTaste, group: Group, region: Region): Set<Genre> {
        val decades = decadeTaste.values.map { it.toRange() }
        val providers = group.providers

        val aggregated = mutableSetOf<Genre>()
        for (decade in decades) {
            for (provider in providers) {
                val titles = fetchTitlesForDecade(provider, decade)

                val genres = extractGenres(titles, region)

                aggregated.addAll(genres)
            }
        }

        return aggregated
    }

    private fun fetchTitlesForDecade(provider: Provider, decade: IntRange): Set<Title> {
        val titles = provider.toJwProvider().titles()

        return titles.filter().by(
            RELEASE_YEAR_FROM to decade.first,
            RELEASE_YEAR_UNTIL to decade.last
        ).second
    }

    private fun extractGenres(titles: Set<Title>, region: Region): List<Genre> {
        val genreIds = titles.map { it.details() }
            .map { it.genreIds }
            .flatten()
            .toSet()

        return genreIds.mapNotNull { region.genres().get(it) }
    }

    private fun Provider.toJwProvider(): JwProvider {
        val context = Context()
        context[Context.Key.REGION] = "en_US"
        val provider = JwProvider(name, shorthand)
        provider.context = context

        return provider
    }
}
