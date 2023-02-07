package com.example.jobads.infrastructure

import com.example.jobads.application.JobAd
import com.example.jobads.application.JobAdsClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.net.URI
import java.time.Duration
import java.time.OffsetDateTime

class HttpJobAdsClient(
    private val webClient: WebClient,
    private val baseUri: URI,
    private val authToken: String,
) : JobAdsClient {
    /**
     * @return The second pair returns the current page and a boolean representing if we have reached the last page.
     */
    override fun getJobAds(from: OffsetDateTime, to: OffsetDateTime, page: Int): Pair<List<JobAd>, Pair<Int, Boolean>> {
        val uri =
            baseUri.resolve("/public-feed/api/v1/ads?published=[${from.toLocalDateTime()},${to.toLocalDateTime()}]&page=$page&size=20")
        return runCatching {
            webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer $authToken")
                .retrieve()
                .bodyToMono<AdsJsonResponse>()
                .block(Duration.ofSeconds(10))
                ?.let {
                    it.content.map(AdJsonResponse::toDomain) to (it.pageNumber to it.last)
                }
                ?: (emptyList<JobAd>() to (0 to true))
        }.getOrElse {
            // Print and ignore any errors to get an approximate
            println(it)
            (emptyList<JobAd>() to (page to false))
        }
    }
}
