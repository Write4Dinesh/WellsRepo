package com.example.myapplication.model.http.services.topheadlines

import com.google.gson.annotations.SerializedName

data class NFSource(@SerializedName("name")
                  val name: String = "",
                    @SerializedName("id")
                  val id: String = "")