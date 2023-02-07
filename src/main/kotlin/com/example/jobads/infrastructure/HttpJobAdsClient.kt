package com.example.jobads.infrastructure

import com.example.jobads.application.JobAd
import com.example.jobads.application.JobAdsClient
import com.fasterxml.jackson.annotation.JsonProperty
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
    // pair.second is true if this was the last list
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
            println(it)
            (emptyList<JobAd>() to (0 to true))
        }
    }
}

data class AdsJsonResponse(
    val content: List<AdJsonResponse>,
    val totalElements: Int,
    val pageNumber: Int,
    val totalPages: Int,
    val first: Boolean,
    val last: Boolean,
    val sort: String,
)

data class AdJsonResponse(
    val uuid: String,
    val title: String,
    @JsonProperty("jobtitle")
    val jobTitle: String?,
    val description: String,
    val published: OffsetDateTime,
    val expires: OffsetDateTime,
) {
    fun toDomain(): JobAd = JobAd(
        uuid = uuid,
        title = title,
        jobTitle = jobTitle,
        description = description,
        published = published,
        expires = expires,
    )
}
