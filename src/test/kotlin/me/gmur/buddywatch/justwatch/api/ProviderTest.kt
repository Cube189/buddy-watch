package me.gmur.buddywatch.justwatch.api

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain

class ProviderTest : ShouldSpec({

    should("return all providers available in the US") {
        val expectedProvider = Provider("Netflix", "nfx")

        val region = Region("en_US", "US", "United States")
        val providers = region.providers().available()

        providers shouldContain expectedProvider
    }

})
