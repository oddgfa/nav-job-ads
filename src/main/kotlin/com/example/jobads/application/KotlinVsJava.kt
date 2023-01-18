package com.example.jobads.application

import java.time.temporal.WeekFields
import java.util.*

class KotlinVsJava(
    private val jobAdsClient: JobAdsClient,
) {
    fun kotlinVsJava(): Map<String, KotlinVsJavaCount> {
        val jobAds = jobAdsClient.getJobAds()
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

        return count
    }
}

data class KotlinVsJavaCount(
    val kotlin: Int,
    val java: Int,
)