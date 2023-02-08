package com.example.jobads.application

import java.time.OffsetDateTime

interface JobAdsClient {
    fun getJobAds(from: OffsetDateTime, to: OffsetDateTime): Sequence<List<JobAd>>
}