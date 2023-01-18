package com.example.jobads

import com.example.jobads.infrastructure.HttpJobAdsApi
import org.springframework.web.servlet.function.router

class JobAdsRouter(
    private val jobAdsApi: HttpJobAdsApi,
) {
    val router = router {
        GET("/kotlinVsJava", jobAdsApi::kotlinVsJava)
    }
}