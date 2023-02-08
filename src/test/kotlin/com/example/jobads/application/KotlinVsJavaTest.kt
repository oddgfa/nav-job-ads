package com.example.jobads.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.OffsetDateTime
import java.time.ZoneOffset

class KotlinVsJavaTest {
    private val jobAdsClient = mock<JobAdsClient>()

    private val kotlinVsJava = KotlinVsJava(jobAdsClient)

    @Test
    fun `should count one java ad and one kotlin ad`() {
        val jobAds = listOf(
            JobAd(
                uuid = "1",
                title = "Kotlin developer",
                jobTitle = null,
                description = "I need a kotlin developer",
                published = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                expires = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            ), JobAd(
                uuid = "2",
                title = "Java developer",
                jobTitle = null,
                description = "I need a java developer",
                published = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                expires = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            )
        )
        whenever(jobAdsClient.getJobAds(any(), any()))
            .thenReturn(sequenceOf(jobAds))

        val result = kotlinVsJava.kotlinVsJava()

        assertThat(result).isEqualTo(
            mapOf("2023-5" to KotlinVsJavaCount(1, 1))
        )
    }

    @Test
    fun `should group by weeks and year`() {
        val jobAds = listOf(
            JobAd(
                uuid = "1",
                title = "Kotlin developer",
                jobTitle = null,
                description = "I need a kotlin developer",
                published = OffsetDateTime.of(2022, 7, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                expires = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            ), JobAd(
                uuid = "2",
                title = "Java developer",
                jobTitle = null,
                description = "I need a java developer",
                published = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                expires = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            )
        )
        whenever(jobAdsClient.getJobAds(any(), any()))
            .thenReturn(sequenceOf(jobAds))

        val result = kotlinVsJava.kotlinVsJava()

        assertThat(result).isEqualTo(
            mapOf(
                "2023-5" to KotlinVsJavaCount(0, 1),
                "2022-26" to KotlinVsJavaCount(1, 0),
            )
        )
    }

    @Test
    fun `should count nothing if no keywords are present`() {
        val jobAds = listOf(
            JobAd(
                uuid = "1",
                title = "developer",
                jobTitle = null,
                description = "I need a developer",
                published = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                expires = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            ), JobAd(
                uuid = "2",
                title = "developer",
                jobTitle = null,
                description = "I need a developer",
                published = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                expires = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            )
        )
        whenever(jobAdsClient.getJobAds(any(), any()))
            .thenReturn(sequenceOf(jobAds))

        val result = kotlinVsJava.kotlinVsJava()

        assertThat(result).isEqualTo(
            mapOf("2023-5" to KotlinVsJavaCount(0, 0))
        )
    }


    @Test
    fun `should count both if both keywords are present`() {
        val jobAds = listOf(
            JobAd(
                uuid = "1",
                title = "Kotlin developer",
                jobTitle = null,
                description = "I meant a Java developer",
                published = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                expires = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            ), JobAd(
                uuid = "2",
                title = "Java developer",
                jobTitle = null,
                description = "I need a Java developer",
                published = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                expires = OffsetDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            )
        )
        whenever(jobAdsClient.getJobAds(any(), any()))
            .thenReturn(sequenceOf(jobAds))

        val result = kotlinVsJava.kotlinVsJava()

        assertThat(result).isEqualTo(
            mapOf("2023-5" to KotlinVsJavaCount(1, 2))
        )
    }
}