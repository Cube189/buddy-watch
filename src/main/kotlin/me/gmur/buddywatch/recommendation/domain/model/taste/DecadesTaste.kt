package me.gmur.buddywatch.recommendation.domain.model.taste

class DecadesTaste(val decades: Set<Decade>)

data class Decade(val value: Int) {

    fun toRange(): IntRange {
        return IntRange(value, value + 9)
    }
}
