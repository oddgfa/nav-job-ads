package com.example.blackjack.application.domain

data class CardValue(
    val textRepresentation: String,
) {
    val value: Int = cardValueToInt(textRepresentation)

    companion object {
        fun cardValueToInt(value: String) = when (value) {
            "J",
            "Q",
            "K",
            -> 10

            "A" -> 11

            else -> value.toInt()
        }
    }
}