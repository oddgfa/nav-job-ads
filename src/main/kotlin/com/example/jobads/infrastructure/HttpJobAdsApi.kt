package com.example.jobads.infrastructure

import com.example.jobads.application.KotlinVsJava
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class HttpJobAdsApi(
    private val kotlinVsJava: KotlinVsJava,
) {
    fun kotlinVsJava(request: ServerRequest): ServerResponse {
        kotlinVsJava.kotlinVsJava()
        return ServerResponse.ok().build()
    }
}

