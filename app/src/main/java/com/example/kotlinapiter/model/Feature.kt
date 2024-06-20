package com.example.kotlinapiter.model

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
)