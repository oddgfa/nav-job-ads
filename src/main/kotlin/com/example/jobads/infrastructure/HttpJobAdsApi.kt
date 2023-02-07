package com.example.jobads.infrastructure

import com.example.jobads.application.KotlinVsJava
import com.example.jobads.application.KotlinVsJavaCount
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class HttpJobAdsApi(
    private val kotlinVsJava: KotlinVsJava,
) {
    fun kotlinVsJava(request: ServerRequest): ServerResponse {
        val count = kotlinVsJava.kotlinVsJava()
        return ServerResponse.ok().body(
            count.mapValues { KotlinVsJavaResponseJson.fromDomain(it.value) }
        )
    }
}

data class KotlinVsJavaResponseJson(
    val kotlin: Int,
    val java: Int,
) {
    companion object {
        fun fromDomain(kotlinVsJava: KotlinVsJavaCount): KotlinVsJavaResponseJson = KotlinVsJavaResponseJson(
            kotlin = kotlinVsJava.kotlin,
            java = kotlinVsJava.java,
        )
    }
}

