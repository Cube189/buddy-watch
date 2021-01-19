package me.gmur.buddywatch.recommendation.adapter.external

import me.gmur.buddywatch.justwatch.api.Context
import me.gmur.buddywatch.justwatch.api.JwCastMember
import me.gmur.buddywatch.justwatch.api.JwFilterParam
import me.gmur.buddywatch.justwatch.api.JwProvider
import me.gmur.buddywatch.justwatch.api.JwTitle
import me.gmur.buddywatch.justwatch.api.JwTitleDetails
import me.gmur.buddywatch.provider.domain.model.Provider
import me.gmur.buddywatch.recommendation.domain.model.CastMember
import me.gmur.buddywatch.recommendation.domain.model.FetchedMovie
import me.gmur.buddywatch.recommendation.domain.port.MovieClient
import org.springframework.stereotype.Service

@Service
class JustWatchMovieClient : MovieClient {

    override fun allFor(decades: List<Int>, providers: Set<Provider>): List<FetchedMovie> {
        val movies = decades.map { fetchFor(it, providers) }.flatten()

        val aggregated = mutableListOf<FetchedMovie>()
        for (pair in movies) {
            val provider = pair.first.let { Provider(it.shorthand, it.name) }
            val details = pair.second.map { it.details() }

            val movies = details.mapNotNull { it.toMovie(provider) }

            aggregated.addAll(movies)
        }

        return aggregated
    }

    private fun fetchFor(decade: Int, providers: Set<Provider>): List<Pair<JwProvider, List<JwTitle>>> {
        val jwProviders = providers.map { it.toJwProvider() }

        val aggregated = mutableListOf<Pair<JwProvider, List<JwTitle>>>()
        for (provider in jwProviders) {
            val titles = (0..1).map { page ->
                provider.titles().filter().by(
                    JwFilterParam.RELEASE_YEAR_FROM to decade,
                    JwFilterParam.RELEASE_YEAR_UNTIL to decade + 9,
                    JwFilterParam.CONTENT_TYPES to arrayOf("movie"),
                    JwFilterParam.PAGE to page
                )
            }.map { it.second }.flatten()

            aggregated.add(Pair(provider, titles))
        }

        return aggregated.toList()
    }

    private fun Provider.toJwProvider(): JwProvider {
        val context = Context()
        context[Context.Key.REGION] = "en_US"

        val jwProvider = JwProvider(name, shorthand)
        jwProvider.context = context

        return jwProvider
    }

    private fun JwTitleDetails.toMovie(provider: Provider): FetchedMovie? {
        return try {
            val cast = cast.map { it.toCastMember() }

            FetchedMovie(
                title,
                description,
                released,
                cast,
                genreIds,
                reference = id,
                provider.shorthand
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun JwCastMember.toCastMember(): CastMember {
        return CastMember(name, role?.name ?: "<unknown>", reference = id)
    }
}
