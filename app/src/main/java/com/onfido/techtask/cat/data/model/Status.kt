package com.onfido.techtask.cat.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    @SerialName("verified") val verified: Boolean,
    @SerialName("sentCount") val sentCount: Int
)
