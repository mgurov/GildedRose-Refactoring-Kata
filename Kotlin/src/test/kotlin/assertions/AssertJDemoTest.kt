package assertions

import com.gildedrose.Item
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * see also https://assertj.github.io/doc/#assertj-core
 */
class AssertJDemoTest {

    @Test
    fun `show simple assertion`() {
        assertThat("blah").isEqualTo("blah")
        assertThat("blah").startsWith("bl")
    }

    @Test
    fun `show collection assertions`() {
        assertThat(
            listOf(
                Item("a", 1, 10),
                Item("b", 2, 20),
            ).map {
                it.name to it.quality
            }
        ).containsExactlyInAnyOrder(
            "a" to 10,
            "b" to 20,
        )
    }

}