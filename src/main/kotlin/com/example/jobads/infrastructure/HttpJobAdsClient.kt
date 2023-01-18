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
    override fun getJobAds(): List<JobAd> {
        return webClient.get()
            .uri(baseUri.resolve("/public-feed/api/v1/ads"))
            .header("Authorization", "Bearer $authToken")
            .retrieve()
            .bodyToMono<AdsJsonResponse>()
            .block(Duration.ofSeconds(10))
            ?.content
            ?.map(AdJsonResponse::toDomain)
            ?: emptyList()
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
