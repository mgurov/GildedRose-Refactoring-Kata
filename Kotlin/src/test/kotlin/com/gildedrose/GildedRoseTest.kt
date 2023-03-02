package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun foo() {
        val items = listOf(Item("foo", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("foo", app.items[0].name)

    }

    @Test
    fun `legacy simulatoin should work as it worked before`() {
        val items = listOf(Item("+5 Dexterity Vest", 10, 20), //
            Item("Aged Brie", 2, 0), //
            Item("Elixir of the Mongoose", 5, 7), //
            Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            Item("Sulfuras, Hand of Ragnaros", -1, 80),
            Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            Item("Conjured Mana Cake", 3, 6))

        val app = GildedRose(items)

        assertEquals(
            """
                +5 Dexterity Vest, 10, 20
                Aged Brie, 2, 0
                Elixir of the Mongoose, 5, 7
                Sulfuras, Hand of Ragnaros, 0, 80
                Sulfuras, Hand of Ragnaros, -1, 80
                Backstage passes to a TAFKAL80ETC concert, 15, 20
                Backstage passes to a TAFKAL80ETC concert, 10, 49
                Backstage passes to a TAFKAL80ETC concert, 5, 49
                Conjured Mana Cake, 3, 6
            """.trimIndent(),
            items.map { it.toString() }.joinToString(separator = "\n")
        )


        app.updateQuality()
        assertEquals(
            """
                +5 Dexterity Vest, 9, 19
                Aged Brie, 1, 1
                Elixir of the Mongoose, 4, 6
                Sulfuras, Hand of Ragnaros, 0, 80
                Sulfuras, Hand of Ragnaros, -1, 80
                Backstage passes to a TAFKAL80ETC concert, 14, 21
                Backstage passes to a TAFKAL80ETC concert, 9, 50
                Backstage passes to a TAFKAL80ETC concert, 4, 50
                Conjured Mana Cake, 2, 5
            """.trimIndent(), items.map { it.toString() }.joinToString(separator = "\n")
        )

        //day2
        app.updateQuality()
        assertEquals(
            """
                +5 Dexterity Vest, 8, 18
                Aged Brie, 0, 2
                Elixir of the Mongoose, 3, 5
                Sulfuras, Hand of Ragnaros, 0, 80
                Sulfuras, Hand of Ragnaros, -1, 80
                Backstage passes to a TAFKAL80ETC concert, 13, 22
                Backstage passes to a TAFKAL80ETC concert, 8, 50
                Backstage passes to a TAFKAL80ETC concert, 3, 50
                Conjured Mana Cake, 1, 4
            """.trimIndent(), items.map { it.toString() }.joinToString(separator = "\n")
        )

        //day3
        app.updateQuality()
        assertEquals(
            """
                +5 Dexterity Vest, 7, 17
                Aged Brie, -1, 4
                Elixir of the Mongoose, 2, 4
                Sulfuras, Hand of Ragnaros, 0, 80
                Sulfuras, Hand of Ragnaros, -1, 80
                Backstage passes to a TAFKAL80ETC concert, 12, 23
                Backstage passes to a TAFKAL80ETC concert, 7, 50
                Backstage passes to a TAFKAL80ETC concert, 2, 50
                Conjured Mana Cake, 0, 3
            """.trimIndent(), items.map { it.toString() }.joinToString(separator = "\n")
        )

        //day4
        app.updateQuality()
        assertEquals(
            """
            +5 Dexterity Vest, 6, 16
            Aged Brie, -2, 6
            Elixir of the Mongoose, 1, 3
            Sulfuras, Hand of Ragnaros, 0, 80
            Sulfuras, Hand of Ragnaros, -1, 80
            Backstage passes to a TAFKAL80ETC concert, 11, 24
            Backstage passes to a TAFKAL80ETC concert, 6, 50
            Backstage passes to a TAFKAL80ETC concert, 1, 50
            Conjured Mana Cake, -1, 1
            """.trimIndent(), items.map { it.toString() }.joinToString(separator = "\n")
        )

    }

}


