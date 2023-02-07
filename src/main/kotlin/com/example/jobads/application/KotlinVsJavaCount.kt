package com.example.jobads.application

data class KotlinVsJavaCount(
    val kotlin: Int,
    val java: Int,
) {
    operator fun plus(kotlinVsJavaCount: KotlinVsJavaCount) = KotlinVsJavaCount(
        kotlin = this.kotlin + kotlinVsJavaCount.kotlin,
        java = this.java + kotlinVsJavaCount.java,
    )
}