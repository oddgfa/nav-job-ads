package com.example.jobads

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JobAdsApplication

fun main(args: Array<String>) {
    runApplication<JobAdsApplication>(*args) {
        addInitializers(jobAdsProdBeans)
    }
}
