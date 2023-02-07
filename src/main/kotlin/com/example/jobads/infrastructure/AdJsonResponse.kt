package com.example.jobads.infrastructure

import com.example.jobads.application.JobAd
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

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