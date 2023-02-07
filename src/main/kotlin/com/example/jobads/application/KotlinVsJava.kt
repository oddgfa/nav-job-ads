package com.example.jobads.application

import com.fasterxml.jackson.databind.ObjectMapper
import java.time.OffsetDateTime
import java.time.temporal.WeekFields
import java.util.*

class KotlinVsJava(
    private val jobAdsClient: JobAdsClient,
) {
    fun kotlinVsJava(): Map<String, KotlinVsJavaCount> {
        val now = OffsetDateTime.now()
        val halfAYearAgo = now.minusMonths(6)

        val acc = mutableMapOf<String, KotlinVsJavaCount>()
        var pageNumber = 0
        do {
            val result = jobAdsClient.getJobAds(halfAYearAgo, now, pageNumber)

            if (result.first.isEmpty()) {
                return acc
            }

            pageNumber = result.second.first + 1
            val jobAds = result.first
                .groupBy {
                    val weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
                    "${it.published.year}-${it.published.get(weekOfYear)}"
                }

            val count = jobAds.mapValues {
                it.value.fold(KotlinVsJavaCount(0, 0)) { acc, jobAd ->
                    when {
                        jobAd.contains("kotlin") -> KotlinVsJavaCount(acc.kotlin + 1, acc.java)
                        jobAd.contains("java") -> KotlinVsJavaCount(acc.kotlin, acc.java + 1)
                        else -> acc
                    }
                }
            }

            count.forEach { (k, v) -> acc.merge(k, v) { oldVal, newVal -> oldVal + newVal } }
            println(pageNumber)
        } while (!result.second.second)

        println(ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(acc))

        return acc
    }
}

data class KotlinVsJavaCount(
    val kotlin: Int,
    val java: Int,
) {
    operator fun plus(kotlinVsJavaCount: KotlinVsJavaCount) = KotlinVsJavaCount(
        kotlin = this.kotlin + kotlinVsJavaCount.kotlin,
        java = this.java + kotlinVsJavaCount.java,
    )
}