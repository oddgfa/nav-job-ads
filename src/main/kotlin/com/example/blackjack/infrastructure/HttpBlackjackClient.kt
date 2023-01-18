package com.example.blackjack.infrastructure

import com.example.blackjack.application.BlackjackClient
import com.example.blackjack.application.domain.Card
import com.example.blackjack.application.domain.CardSuite
import com.example.blackjack.application.domain.CardValue
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI
import java.time.Duration

class HttpBlackjackClient(
    private val webClient: WebClient,
    private val uri: URI,
) : BlackjackClient {

    override fun shuffle(): List<Card> {
        return webClient
            .get()
            .uri(uri)
            .retrieve()
            .bodyToMono(Array<CardResponseJson>::class.java)
            .block(Duration.ofSeconds(10))
            ?.map(CardResponseJson::toDomain)
            ?: emptyList()
    }
}

data class CardResponseJson(
    val suit: CardSuiteJson,
    val value: String,
) {
    fun toDomain(): Card = Card(
        suite = suit.toDomain(),
        value = CardValue(value)
    )
}

enum class CardSuiteJson {
    HEARTS, DIAMONDS, SPADES, CLUBS;

    fun toDomain(): CardSuite = when (this) {
        HEARTS -> CardSuite.HEARTS
        DIAMONDS -> CardSuite.DIAMONDS
        SPADES -> CardSuite.SPADES
        CLUBS -> CardSuite.CLUBS
    }
}