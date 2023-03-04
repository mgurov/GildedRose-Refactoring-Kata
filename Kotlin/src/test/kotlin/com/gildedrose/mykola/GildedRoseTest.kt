package com.gildedrose.mykola

import com.gildedrose.GildedRose
import com.gildedrose.Item
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class GildedRoseTest {

    @Test
    fun foo() {
        val items = listOf(Item("foo", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("foo", app.items[0].name)

    }

    @Test
    fun `basic requirements`() {
        val theItem = Item("foo", sellIn = 2, quality = 10)
        val app = GildedRose(listOf(theItem))
        assertEquals("foo, 2, 10", theItem.toString())
        app.updateQuality()
        assertEquals("foo, 1, 9", theItem.toString())
    }

    @Test
    fun `Once the sell by date has passed, Quality degrades twice as fast`() {
        assertThat(
            Item("foo", sellIn = 2, quality = 10)
                .exhaust()
        ).containsExactly(
            10, 9, 8, 6, 4, 2, 0
        )
    }

    @Test
    fun `quality of an item is never negative`() {
        val item = Item("foo", sellIn = 0, quality = 0)
        GildedRose(listOf(item)).updateQuality()
        assertThat(item.quality).isEqualTo(0)
    }

    @Test
    fun `Aged Brie actually increases in Quality the older it gets`() {
        val item = Item("Aged Brie", sellIn = 1, quality = 0)
        GildedRose(listOf(item)).updateQuality()
        assertThat(item.quality).isEqualTo(1)
    }

    @Test
    fun `Aged Brie ages twice as fast after expiration apparently`() {
        val item = Item("Aged Brie", sellIn = 0, quality = 0)
        GildedRose(listOf(item)).updateQuality()
        assertThat(item.quality).isEqualTo(2)
    }

    @Disabled //TODO: fixme
    @Test
    fun `The Quality of an item is never more than 50`() {
        val item = Item("foo", sellIn = 1, quality = 100)
        GildedRose(listOf(item)).updateQuality()
        assertThat(item.quality).isEqualTo(50)
    }

    @Test
    fun `The Quality of an item is never more than 50 - the case of the aged brie`() {
        val item = Item("Aged Brie", sellIn = 1, quality = 50)
        GildedRose(listOf(item)).updateQuality()
        assertThat(item.quality).isEqualTo(50)
    }

    @Test
    fun `the quality should be lowered at the end of the day`() {
        //given
        val theItem = Item("foo", sellIn = 2, quality = 10)
        val app = GildedRose(listOf(theItem))
        //when
        app.updateQuality()
        //then
        assertEquals(9, theItem.quality)
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

//TODO: assumes we never go below zero.
private fun Item.exhaust(): List<Int> {
    val app = GildedRose(listOf(this))
    val result = mutableListOf<Int>()
    while (true) {
        val todayQuantity = this.quality
        result += todayQuantity
        if (0 == todayQuantity)  {
            return result
        }
        app.updateQuality()
    }
}


