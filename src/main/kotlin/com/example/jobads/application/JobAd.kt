package com.example.jobads.application

import java.time.OffsetDateTime

data class JobAd(
    val uuid: String,
    val title: String,
    val jobTitle: String?,
    val description: String,
    val published: OffsetDateTime,
    val expires: OffsetDateTime,
) {
    fun contains(value: String): Boolean =
        title.contains(value, ignoreCase = true) ||
                description.contains(value, ignoreCase = true) ||
                jobTitle?.let { jobTitle.contains(value, ignoreCase = true) } == true
}
