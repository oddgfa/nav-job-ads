package com.example.blackjack.application.domain

data class DeckOfCards(
    private val cards: List<Card>
) {
    private val deck = cards.toMutableList()

    fun drawCard() = deck.removeFirst()
}
