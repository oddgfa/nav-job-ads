package com.example.blackjack.application.domain

sealed class GameState {
    data class Winner(val playerId: PlayerId) : GameState()
    object Tie : GameState()
    object Inconclusive : GameState()
}