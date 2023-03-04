package com.gildedrose

typealias SellInDelta = (sellIn: Int, quality: Int) -> Int
val defaultDepreciation: SellInDelta = { sellIn, _ -> if (sellIn >= 0) -1 else -2 } //by default depreciation 1 by default, -2 after deadline

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            val item = items[i]
            updateDailyQuantity(item)
            //updateDailyQuantityLegacy(item)
        }
    }

    private fun updateDailyQuantity(item: Item) {

        if (item.name == Sulfuras) {
            return // Sulfuras is a legendary item. TODO: enforce 80!
        }

        item.sellIn = item.sellIn - 1

        val sellInDelta: SellInDelta = when {
            item.name == BackstagePasses -> { sellIn, quality ->
                if (sellIn < 0) {
                    -quality
                } else if (sellIn < 5) {
                    3
                } else if (sellIn < 10) {
                    2
                } else {
                    1
                }
            }
            item.name == AgedBrie -> { sellIn, _ -> if (sellIn >= 0) 1 else 2 }
            item.name.startsWith("Conjured") -> { sellIn, quality -> defaultDepreciation(sellIn, quality) * 2 }
            else -> defaultDepreciation
        }

        val delta = sellInDelta(item.sellIn, item.quality)
        item.quality = (item.quality + delta).coerceAtLeast(0).coerceAtMost(50)
    }

    private fun updateDailyQuantityLegacy(item: Item) {

        if (item.name != AgedBrie && item.name != BackstagePasses) {
            if (item.quality > 0) {
                if (item.name != Sulfuras) {
                    item.quality = item.quality - 1
                }
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1

                if (item.name == BackstagePasses) {
                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1
                        }
                    }
                }
            }
        }

        if (item.name != Sulfuras) {
            item.sellIn = item.sellIn - 1
        }

        if (item.sellIn < 0) {
            if (item.name != AgedBrie) {
                if (item.name != BackstagePasses) {
                    if (item.quality > 0) {
                        if (item.name != Sulfuras) {
                            item.quality = item.quality - 1
                        }
                    }
                } else {
                    item.quality = item.quality - item.quality
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1
                }
            }
        }
    }
}

const val Sulfuras = "Sulfuras, Hand of Ragnaros"
const val AgedBrie = "Aged Brie"
const val BackstagePasses = "Backstage passes to a TAFKAL80ETC concert" //TODO: make reusable?


