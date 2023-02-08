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

        val count = jobAdsClient.getJobAds(halfAYearAgo, now)
            .fold(mutableMapOf<String, KotlinVsJavaCount>()) { acc, jobAds ->
                val grouped = groupByWeek(jobAds)

                val count = countKotlinVsJava(grouped)

                mergeCount(count, acc)
            }

        println(ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(count))

        return count
    }

    private fun mergeCount(
        count: Map<String, KotlinVsJavaCount>,
        acc: MutableMap<String, KotlinVsJavaCount>
    ): MutableMap<String, KotlinVsJavaCount> {
        count.forEach { (k, v) -> acc.merge(k, v) { oldVal, newVal -> oldVal + newVal } }
        return acc
    }

    private fun countKotlinVsJava(grouped: Map<String, List<JobAd>>): Map<String, KotlinVsJavaCount> {
        val count = grouped.mapValues {
            it.value.fold(KotlinVsJavaCount(0, 0)) { acc, jobAd ->
                val kotlinCount = if (jobAd.contains("kotlin")) 1 else 0
                val javaCount = if (jobAd.contains("java")) 1 else 0
                acc + KotlinVsJavaCount(kotlinCount, javaCount)
            }
        }
        return count
    }

    private fun groupByWeek(jobAds: List<JobAd>): Map<String, List<JobAd>> {
        val weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
        val grouped = jobAds.groupBy {
            "${it.published.year}-${it.published.get(weekOfYear)}"
        }
        return grouped
    }
}

