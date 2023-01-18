package com.example.blackjack.infrastructure

import com.example.blackjack.application.BlackjackService
import com.example.blackjack.application.domain.GameState
import com.example.blackjack.application.domain.PlayerId
import com.example.blackjack.application.domain.PlayerScore
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class HttpBlackjackApi(
    private val blackjackService: BlackjackService,
) {
    fun shuffle(request: ServerRequest): ServerResponse {
        blackjackService.shuffle()
        return ServerResponse.ok().build()
    }

    fun startGame(request: ServerRequest): ServerResponse {
        val json = request.body(StartGameRequestJson::class.java)

        val result = blackjackService.playGame(PlayerId(json.player1), PlayerId(json.player2))

        println(result)

        return ServerResponse.ok().body(
            ScoreBoardResponseJson(
                player1 = PlayerScoreJson.fromDomain(result.player1),
                player2 = PlayerScoreJson.fromDomain(result.player2),
                gameState = GameStateJson.fromDomain(result.gameState)
            )
        )
    }
}

data class StartGameRequestJson(
    val player1: String,
    val player2: String,
)

data class ScoreBoardResponseJson(
    val player1: PlayerScoreJson,
    val player2: PlayerScoreJson,
    val gameState: GameStateJson,
)

data class PlayerScoreJson(
    val playerId: String,
    val drawnCards: List<String>,
    val score: Int,
) {
    companion object {
        fun fromDomain(playerScore: PlayerScore) = PlayerScoreJson(
            playerId = playerScore.playerId.id,
            drawnCards = playerScore.drawnCards.map { it.toString() },
            score = playerScore.score
        )
    }
}

data class GameStateJson(
    val gameState: String,
    val winner: String?,
) {
    companion object {
        fun fromDomain(gameState: GameState) = GameStateJson(
            gameState = gameState.javaClass.simpleName, winner = when (gameState) {
                GameState.Inconclusive,
                GameState.Tie,
                -> null

                is GameState.Winner -> gameState.playerId.id
            }
        )
    }
}
