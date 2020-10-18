package me.gmur.buddywatch

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.endWith
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DummyMockingTest : BehaviorSpec({

    given("a dummy Deep Thought service") {
        val service = mockk<Dummy>()
        every { service.foo() } returns 42

        `when`("foo() gets called") {
            val answer = service.foo()

            then("it should return the answer to everything") {
                answer shouldBe 42
            }
        }
    }

    given("a dummy service") {
        val service = mockk<Dummy>()
        every { service.bar() } returns "foobar"

        `when`("bar() gets called") {
            val result = service.bar()

            then("it should be called only once") {
                verify { service.bar() }
            }
            and("the returned result should end with 'bar'") {
                result should endWith("bar")
            }
        }
    }
})
