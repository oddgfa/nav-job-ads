package com.example.blackjack

import com.example.blackjack.infrastructure.HttpBlackjackApi
import org.springframework.web.servlet.function.router

class BlackjackRouter(
    private val blackjackApi: HttpBlackjackApi,
) {
    val router = router {
        GET("/shuffle", blackjackApi::shuffle)
        POST("/startGame", blackjackApi::startGame)
    }
}