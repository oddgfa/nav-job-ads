package com.example.jobads

import com.example.jobads.application.KotlinVsJava
import com.example.jobads.infrastructure.HttpJobAdsApi
import com.example.jobads.infrastructure.HttpJobAdsClient
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI

val jobAdsProdBeans = beans {
    bean {
        WebClient.create()
    }
    bean<JobAdsRouter>()
    bean {
        ref<JobAdsRouter>().router
    }

    bean {
        HttpJobAdsClient(ref(), URI.create(env.getRequiredProperty("http.client.jobads.base.url")), env.getRequiredProperty("http.client.jobads.auth.token"))
    }
    bean<HttpJobAdsApi>()

    bean<KotlinVsJava>()
}
