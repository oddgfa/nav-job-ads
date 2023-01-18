package com.example.blackjack.application.domain

data class PlayerScore(
    val playerId: PlayerId,
    val drawnCards: MutableList<Card>,
) {
    val score: Int
        get() = drawnCards.fold(0) { acc, card ->
            acc + card.value.value
        }

    override fun toString(): String {
        return "${playerId.id.take(10).padEnd(10)} | ${score.toString().padEnd(2)} | ${drawnCards.joinToString()}"
    }

    fun drawCard(deck: DeckOfCards): PlayerScore =
        also {
            this.drawnCards.add(deck.drawCard())
        }
}