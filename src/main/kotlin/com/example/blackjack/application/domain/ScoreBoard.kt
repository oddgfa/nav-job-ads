package com.example.blackjack.application.domain

data class ScoreBoard(
    val player1: PlayerScore,
    val player2: PlayerScore,
    val gameState: GameState,
) {
    override fun toString(): String {
        val gameStateString = when (gameState) {
            GameState.Inconclusive -> ""
            GameState.Tie -> "Tie"
            is GameState.Winner -> "Winner: ${gameState.playerId.id}"
        }
        return "$gameStateString\n$player1\n$player2"
    }
}