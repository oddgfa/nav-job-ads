package com.example.jobads.application

import java.time.OffsetDateTime

interface JobAdsClient {
    fun getJobAds(from: OffsetDateTime, to: OffsetDateTime, page: Int): Pair<List<JobAd>, Pair<Int, Boolean>>
}