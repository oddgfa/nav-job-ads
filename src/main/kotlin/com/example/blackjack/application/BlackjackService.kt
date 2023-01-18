package com.example.blackjack.application

import com.example.blackjack.application.domain.*

class BlackjackService(
    private val blackjackClient: BlackjackClient,
) {
    val blackjack = 21
    fun shuffle() {
        val deck = blackjackClient.shuffle()
        println(deck)
    }

    fun playGame(player1: PlayerId, player2: PlayerId): ScoreBoard {
        val deck = DeckOfCards(blackjackClient.shuffle())

        // Both players draw 2 cards
        val player1Score = PlayerScore(
            playerId = player1,
            drawnCards = (1..2).map { deck.drawCard() }.toMutableList()
        )
        val player2Score = PlayerScore(
            playerId = player2,
            drawnCards = (1..2).map { deck.drawCard() }.toMutableList()
        )

        // Check if anyone gets blackjack on the initial draws
        val gameState = earlyWin(player1Score, player2Score)

        ScoreBoard(player1Score, player2Score, gameState).let {
            when (it.gameState) {
                GameState.Inconclusive -> Unit
                GameState.Tie,
                is GameState.Winner,
                -> return it
            }
        }

        while (player1Score.score < 17) {
            player1Score.drawCard(deck)
            if (player1Score.score > blackjack) {
                return ScoreBoard(player1Score, player2Score, GameState.Winner(player2))
            }
        }

        while (player2Score.score < player1Score.score) {
            player2Score.drawCard(deck)
            if (player2Score.score > blackjack) {
                return ScoreBoard(player1Score, player2Score, GameState.Winner(player1))
            }
        }

        return ScoreBoard(player1Score, player1Score, GameState.Winner(player2))
    }

    private fun earlyWin(player1: PlayerScore, player2: PlayerScore): GameState = when {
        // If both get blackjack it's a tie
        player1.score == blackjack && player2.score == blackjack -> GameState.Tie
        player1.score == blackjack -> GameState.Winner(player1.playerId)
        player2.score == blackjack -> GameState.Winner(player2.playerId)
        else -> GameState.Inconclusive
    }

}

