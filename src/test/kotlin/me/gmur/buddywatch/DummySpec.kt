package me.gmur.buddywatch

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith

class DummySpec : StringSpec({

    "An empty set should return size of zero" {
        val set = setOf<Int>()

        set shouldHaveSize 0
    }

    "Addition of two 2s should result in a 4" {
        2 + 2 shouldBe 4
    }

    "'Foobar' should start with 'Foo'" {
        val string = "Foobar"

        string shouldStartWith "Foo"
    }
})
