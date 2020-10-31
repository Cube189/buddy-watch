package me.gmur.buddywatch.justwatch.api

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain

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

})
