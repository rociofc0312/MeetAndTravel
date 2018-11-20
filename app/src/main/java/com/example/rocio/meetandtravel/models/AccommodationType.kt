package com.example.rocio.meetandtravel.models

import android.os.Bundle
import com.google.gson.annotations.SerializedName

data class AccommodationType(
        val id: Int,
        val name: String? = "",
        @SerializedName("start_date")
        val startDate:String? = "",
        @SerializedName("end_date")
        val endDate:String? = "",
        @SerializedName("total_cost")
        val totalCost: Float,
        val characteristics:String? = "",
        val spaces: Int,
        @SerializedName("provider_id")
        val providerId: String? = null,
        val provider: Provider? = null){
    companion object {
        fun from(bundle: Bundle) : AccommodationType {
            return AccommodationType(
                    bundle.getInt("id"),
                    bundle.getString("name"),
                    bundle.getString("start_date"),
                    bundle.getString("end_date"),
                    bundle.getFloat("total_cost"),
                    bundle.getString("characteristics"),
                    bundle.getInt("spaces"),
                    bundle.getString("provider_id"),
                    Provider.from(bundle.getBundle("provider"))
            )
        }
    }
    fun toBundle() : Bundle {
        val bundle = Bundle()
        with(bundle) {
            putInt("id", id)
            putString("name", name)
            putString("start_date", startDate)
            putString("end_date", endDate)
            putFloat("total_cost", totalCost)
            putString("characteristics", characteristics)
            putInt("spaces", spaces)
            putString("provider_id", providerId)
            putBundle("provider_id", provider?.toBundle())
        }
        return bundle
    }
}