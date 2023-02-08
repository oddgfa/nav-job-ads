package com.example.jobads.infrastructure

import com.example.jobads.application.JobAd
import java.time.OffsetDateTime

class JobAdsIterator(
    private val jobAdsClient: HttpJobAdsClient,
    private val from: OffsetDateTime,
    private val to: OffsetDateTime,
    private var page: Int,
) : Iterator<List<JobAd>>, Sequence<List<JobAd>> {
    private var lastPage = false
    override fun hasNext(): Boolean = !lastPage

    override fun next(): List<JobAd> {
        val result = jobAdsClient.getJobAds(from, to, page)
        lastPage = result.second.second
        page = result.second.first + 1
        return result.first
    }

    override fun iterator(): Iterator<List<JobAd>> {
        return this
    }
}