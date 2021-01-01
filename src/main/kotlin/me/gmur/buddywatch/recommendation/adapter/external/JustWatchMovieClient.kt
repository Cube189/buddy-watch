package me.gmur.buddywatch.recommendation.adapter.external

import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.justwatch.api.Context
import me.gmur.buddywatch.justwatch.api.JwCastMember
import me.gmur.buddywatch.justwatch.api.JwFilterParam
import me.gmur.buddywatch.justwatch.api.JwProvider
import me.gmur.buddywatch.justwatch.api.JwProviderCombination
import me.gmur.buddywatch.justwatch.api.JwTitle
import me.gmur.buddywatch.justwatch.api.JwTitleDetails
import me.gmur.buddywatch.recommendation.domain.model.CastMember
import me.gmur.buddywatch.recommendation.domain.model.Movie
import me.gmur.buddywatch.recommendation.domain.port.MovieClient
import org.springframework.stereotype.Service

@Service
class JustWatchMovieClient : MovieClient {

    private val providers = setOf<Provider>()

    override fun allFor(decades: Collection<Int>): List<Movie> {
        val movies = decades.map { fetchForDecade(it) }.flatten()
        val details = movies.map { it.details() }

        return details.map { it.toMovie() }
    }

    private fun fetchForDecade(decade: Int): List<JwTitle> {
        return (0..1).map { page ->
            toJwProviderCombination(providers).titles().filter().by(
                JwFilterParam.RELEASE_YEAR_FROM to decade,
                JwFilterParam.RELEASE_YEAR_UNTIL to decade + 9,
                JwFilterParam.CONTENT_TYPES to arrayOf("movie"),
                JwFilterParam.PAGE to page
            )
        }.map { it.second }.flatten()
    }

    private fun toJwProviderCombination(providers: Set<Provider>): JwProviderCombination {
        val context = Context()
        context[Context.Key.REGION] = "en_US"

        val jwProviders = providers.map { JwProvider(it.name, it.shorthand) }

        val combination = JwProviderCombination(jwProviders)
        combination.context = context

        return combination
    }

    private fun JwTitleDetails.toMovie(): Movie {
        val cast = cast.map { it.toCastMember() }.toSet()

        return Movie(title, description, released, cast, genreIds, reference = id)
    }

    private fun JwCastMember.toCastMember(): CastMember {
        return CastMember(name, role.name, reference = id)
    }
}
