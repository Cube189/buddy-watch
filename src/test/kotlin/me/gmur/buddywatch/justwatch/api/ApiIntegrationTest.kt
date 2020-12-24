package me.gmur.buddywatch.justwatch.api

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION
import me.gmur.buddywatch.recommendation.domain.model.taste.Decade
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste

class ApiIntegrationTest : ShouldSpec({

    should("return all available regions") {
        val expectedRegion = JwRegion("en_US", "US", "United States")

        val result = JwRegions.available()

        result shouldContain expectedRegion
    }

    should("return all providers available in the US") {
        val expectedProvider = JwProvider("Netflix", "nfx")
        val region = JwRegion("en_US", "US", "United States")

        val providers = region.providers().available()

        providers shouldContain expectedProvider
    }

    should("return all titles for a given provider") {
        val provider = Fixtures.provider()

        val titles = provider.titles().all()

        titles.first shouldBeGreaterThan 0
    }

    should("return all movies for a given provider") {
        val provider = Fixtures.provider()

        val movies = provider.titles().movies()

        movies.first shouldBeGreaterThan 0
    }

    should("return all shows for a given provider") {
        val provider = Fixtures.provider()

        val shows = provider.titles().shows()

        shows.first shouldBeGreaterThan 0
    }

    should("return movie details") {
        val title = Fixtures.title()

        val details = title.details()

        details.id shouldBe title.id
        details.title shouldBe title.title
    }
})

private object Fixtures {

    fun title(): JwTitle {
        val context = Context()
        context[REGION] = "en_US"
        context[PROVIDER] = "nfx"
        val title = JwTitle(75269L, "Yellowstone", TitleType.SHOW)
        title.context = context

        return title
    }

    fun provider(): JwProvider {
        val context = Context()
        context[REGION] = "en_US"
        val provider = JwProvider("Netflix", "nfx")
        provider.context = context

        return provider
    }
}
