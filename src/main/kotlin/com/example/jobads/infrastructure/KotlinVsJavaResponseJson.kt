package com.example.jobads.infrastructure

import com.example.jobads.application.KotlinVsJavaCount

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