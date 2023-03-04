package assertions

import com.gildedrose.Item
import io.kotest.assertions.withClue
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.Test

/**
 * see also https://kotest.io/docs/assertions/assertions.html
 */
class KotestAssertionsDemoTest {

    @Test
    fun `show simple assertion`() {
        "blah" shouldBe "blah"

        withClue("for less obvious case we can give a clue what we were testing") {
            "blah" shouldStartWith "bla"
        }
    }

    @Test
    fun `show collections asserted`() {
        listOf(
            Item("blah", 1, 10),
            Item("foo", 2, 20),
        ).map { it.name to it.quality } shouldContainInOrder listOf(
            "blah" to 10,
            "foo" to 20
        )
    }

}