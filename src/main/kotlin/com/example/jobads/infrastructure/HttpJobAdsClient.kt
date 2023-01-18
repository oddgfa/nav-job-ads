package com.example.jobads.infrastructure

import com.example.jobads.application.JobAdsClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.net.URI
import java.time.Duration

class HttpJobAdsClient(
    private val webClient: WebClient,
    private val baseUri: URI,
    private val authToken: String,
) : JobAdsClient {
    override fun getJobAds() {
        webClient.get()
            .uri(baseUri.resolve("/public-feed/api/v1/ads"))
            .header("Authorization", "Bearer $authToken")
            .retrieve()
            .bodyToMono<Any>()
            .block(Duration.ofSeconds(10))
            ?.let {
                println(it)
            }
    }
}
