package com.examonline.app.network.models.memtoclass

import com.google.gson.annotations.SerializedName

data class MemToClassRequest(
    @field:SerializedName("UserID")
    val UserID: String? = null
)
