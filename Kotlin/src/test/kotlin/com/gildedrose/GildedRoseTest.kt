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

        //TODO: check the result day 0
        val expectedDay0 = """
            +5 Dexterity Vest, 10, 20
            Aged Brie, 2, 0
            Elixir of the Mongoose, 5, 7
            Sulfuras, Hand of Ragnaros, 0, 80
            Sulfuras, Hand of Ragnaros, -1, 80
            Backstage passes to a TAFKAL80ETC concert, 15, 20
            Backstage passes to a TAFKAL80ETC concert, 10, 49
            Backstage passes to a TAFKAL80ETC concert, 5, 49
            Conjured Mana Cake, 3, 6
        """.trimIndent()

        assertEquals(expectedDay0, items.map { it.toString() }.joinToString(separator = "\n") )


        app.updateQuality()

        // TODO: check the result day 1

        val days = 2

//        for (i in 0..days - 1) {
//            println("-------- day $i --------")
//            println("name, sellIn, quality")
//            for (item in items) {
//                println(item)
//            }
//            println()
//            app.updateQuality()
//        }

        // assertions

    }

}


