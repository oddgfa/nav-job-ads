package com.example.blackjack.application.domain

data class Card(
    val suite: CardSuite,
    val value: CardValue,
) {
    override fun toString(): String {
        return "${suite.shortForm}${value.textRepresentation}"
    }
}

