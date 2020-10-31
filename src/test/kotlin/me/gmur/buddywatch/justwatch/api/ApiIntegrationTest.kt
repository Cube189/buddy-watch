package me.gmur.buddywatch.justwatch.api

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.comparables.shouldBeGreaterThan
import me.gmur.buddywatch.justwatch.api.Context.Key.REGION

class ApiIntegrationTest : ShouldSpec({

    should("return all available regions") {
        val expectedRegion = Region("en_US", "US", "United States")

        val result = Regions.available()

        result shouldContain expectedRegion
    }

    should("return all providers available in the US") {
        val expectedProvider = Provider("Netflix", "nfx")
        val region = Region("en_US", "US", "United States")

        val providers = region.providers().available()

        providers shouldContain expectedProvider
    }

    should("return all movies for a given provider") {
        val context = Context()
        context[REGION] = "en_US"
        val provider = Provider("Netflix", "nfx")
        provider.context = context

        val movies = provider.titles().all()

        movies.first shouldBeGreaterThan 0
    }

})
