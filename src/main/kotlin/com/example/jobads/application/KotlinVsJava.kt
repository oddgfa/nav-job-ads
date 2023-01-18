package com.example.jobads.application

class KotlinVsJava(
    private val jobAdsClient: JobAdsClient,
) {
    fun kotlinVsJava() {
        jobAdsClient.getJobAds()
    }
}