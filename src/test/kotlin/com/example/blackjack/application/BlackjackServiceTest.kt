package com.example.blackjack.application

import com.example.blackjack.application.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BlackjackServiceTest {
    private val blackjackClient = mock<BlackjackClient>()
    private val blackjackService = BlackjackService(blackjackClient)

    private val player1 = PlayerId("Odd")
    private val player2 = PlayerId("Magnus")

    @Test
    fun `play game should return scoreboard with winner if first player starts with blackjack`() {
        whenever(blackjackClient.shuffle())
            .thenReturn(listOf(
                Card(CardSuite.CLUBS, value = CardValue("11")),
                Card(CardSuite.CLUBS, value = CardValue("10")),
                Card(CardSuite.CLUBS, value = CardValue("2")),
                Card(CardSuite.CLUBS, value = CardValue("3")),
            ))

        val result = blackjackService.playGame(player1, player2)

        assertThat(result.gameState).isEqualTo(GameState.Winner(player1))
    }

    @Test
    fun `play game should return scoreboard with winner if second player starts with blackjack`() {
        whenever(blackjackClient.shuffle())
            .thenReturn(listOf(
                Card(CardSuite.CLUBS, value = CardValue("2")),
                Card(CardSuite.CLUBS, value = CardValue("3")),
                Card(CardSuite.CLUBS, value = CardValue("11")),
                Card(CardSuite.CLUBS, value = CardValue("10")),
            ))

        val result = blackjackService.playGame(player1, player2)

        assertThat(result.gameState).isEqualTo(GameState.Winner(player2))
    }

    @Test
    fun `play game should return scoreboard with tie if both players start with blackjack`() {
        whenever(blackjackClient.shuffle())
            .thenReturn(listOf(
                Card(CardSuite.HEARTS, value = CardValue("11")),
                Card(CardSuite.HEARTS, value = CardValue("10")),
                Card(CardSuite.CLUBS, value = CardValue("11")),
                Card(CardSuite.CLUBS, value = CardValue("10")),
            ))

        val result = blackjackService.playGame(player1, player2)

        assertThat(result.gameState).isEqualTo(GameState.Tie)
    }

    @Test
    fun `play game should return scoreboard with player 2 as winner when player 1 overdraws`() {
        whenever(blackjackClient.shuffle())
            .thenReturn(listOf(
                Card(CardSuite.CLUBS, value = CardValue("6")),
                Card(CardSuite.CLUBS, value = CardValue("8")),
                Card(CardSuite.CLUBS, value = CardValue("4")),
                Card(CardSuite.CLUBS, value = CardValue("5")),
                Card(CardSuite.CLUBS, value = CardValue("A")),
            ))

        val result = blackjackService.playGame(player1, player2)

        assertThat(result.gameState).isEqualTo(GameState.Winner(player2))
    }

    @Test
    fun `play game should return scoreboard with player 1 as winner when player 2 overdraws`() {
        whenever(blackjackClient.shuffle())
            .thenReturn(listOf(
                Card(CardSuite.CLUBS, value = CardValue("9")),
                Card(CardSuite.CLUBS, value = CardValue("8")),
                Card(CardSuite.CLUBS, value = CardValue("6")),
                Card(CardSuite.CLUBS, value = CardValue("K")),
                Card(CardSuite.CLUBS, value = CardValue("A")),
            ))

        val result = blackjackService.playGame(player1, player2)

        assertThat(result.gameState).isEqualTo(GameState.Winner(player1))
    }

    @Test
    fun `play game should return scoreboard with player 2 as winner when player 2 gets higher score than player 1`() {
        whenever(blackjackClient.shuffle())
            .thenReturn(listOf(
                Card(CardSuite.CLUBS, value = CardValue("9")),
                Card(CardSuite.CLUBS, value = CardValue("8")),
                Card(CardSuite.CLUBS, value = CardValue("6")),
                Card(CardSuite.CLUBS, value = CardValue("K")),
                Card(CardSuite.CLUBS, value = CardValue("3")),
            ))

        val result = blackjackService.playGame(player1, player2)

        assertThat(result.gameState).isEqualTo(GameState.Winner(player2))
    }
}