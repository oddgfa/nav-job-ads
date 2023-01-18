package com.example.blackjack

import com.example.blackjack.application.BlackjackService
import com.example.blackjack.infrastructure.HttpBlackjackApi
import com.example.blackjack.infrastructure.HttpBlackjackClient
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI

val blackjackProdBeans = beans {
    bean {
        WebClient.create()
    }
    bean<BlackjackRouter>()
    bean {
        ref<BlackjackRouter>().router
    }

    bean {
        HttpBlackjackClient(ref(), URI.create(env.getRequiredProperty("http.client.blackjack.base.url")))
    }
    bean<HttpBlackjackApi>()
    bean<BlackjackService>()

}
