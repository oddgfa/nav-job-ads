package com.example.jobads.infrastructure

data class AdsJsonResponse(
    val content: List<AdJsonResponse>,
    val totalElements: Int,
    val pageNumber: Int,
    val totalPages: Int,
    val first: Boolean,
    val last: Boolean,
    val sort: String,
)