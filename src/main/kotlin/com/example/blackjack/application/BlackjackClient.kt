package com.example.blackjack.application

import com.example.blackjack.application.domain.Card

interface BlackjackClient {
    fun shuffle(): List<Card>
}