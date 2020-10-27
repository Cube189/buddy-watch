package me.gmur.buddywatch.justwatch.api

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain

class RegionTest : ShouldSpec({

    should("return all available regions") {
        val expectedRegion = Region("en_US", "US", "United States")

        val result = Regions.available()

        result shouldContain expectedRegion
    }

})
