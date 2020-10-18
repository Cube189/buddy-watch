package me.gmur.buddywatch

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@SpringBootTest
class DummyIntegrationTest : ShouldSpec() {

    // We need this for the Spring context to get picked up correctly
    override fun listeners() = listOf(SpringListener)

    // This is just fluff to make this test interesting,
    // won't be needed in our day-to-day 🙃
    @TestConfiguration
    internal class DummyConfig {
        @Bean
        fun service() = Dummy()
    }

    @Autowired
    private lateinit var service: Dummy

    init {
        context("Dummy") {

            should("return zero when foo() is called") {
                val result = service.foo()

                result shouldBe 0
            }

            should("fail when bar() is called") {
                shouldThrow<IllegalStateException> {
                    service.bar()
                }
            }
        }
    }
}
