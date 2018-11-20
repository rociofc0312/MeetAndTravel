package com.example.rocio.meetandtravel.models

import android.os.Bundle
import com.google.gson.annotations.SerializedName

data class Provider(
        val id: Int,
        val name: String? = "",
        val ruc: String? = "",
        val description:String? = "",
        val location:String? = "",
        val telephone:String? = "",
        @SerializedName("web_address")
        val webAddress:String? = "",
        @SerializedName("provider_image")
        val providerImage:String? = "",
        @SerializedName("user_id")
        val userId:Int){

    companion object {
        fun from(bundle: Bundle) : Provider {
            return Provider(
                    bundle.getInt("id"),
                    bundle.getString("name"),
                    bundle.getString("ruc"),
                    bundle.getString("description"),
                    bundle.getString("location"),
                    bundle.getString("telephone"),
                    bundle.getString("web_address"),
                    bundle.getString("provider_image"),
                    bundle.getInt("user_id")
            )
        }
    }
    fun toBundle() : Bundle {
        val bundle = Bundle()
        with(bundle) {
            putInt("id", id)
            putString("name", name)
            putString("ruc", ruc)
            putString("description", description)
            putString("location", location)
            putString("telephone", telephone)
            putString("web_address", webAddress)
            putString("provider_image", providerImage)
            putInt("user_id", userId)
        }
        return bundle
    }
}