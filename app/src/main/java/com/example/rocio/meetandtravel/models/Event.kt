package com.example.rocio.meetandtravel.models

import android.os.Bundle
import com.google.gson.annotations.SerializedName

data class Event(
        val id: Int,
        val name: String? = "",
        @SerializedName("organized_by")
        val organizedBy: String? = "",
        val description:String? = "",
        @SerializedName("start_date")
        val startDate:String? = "",
        @SerializedName("end_date")
        val endDate:String? = "",
        @SerializedName("start_hour")
        val startHour:String? = "",
        @SerializedName("end_hour")
        val endHour:String? = "",
        val location:String? = "",
        @SerializedName("event_image")
        val eventImage:String? = "",
        @SerializedName("user_id")
        val userId:String? = "") {
        companion object {
        fun from(bundle: Bundle) : Event {
            return Event(
                    bundle.getInt("id"),
                    bundle.getString("name"),
                    bundle.getString("description"),
                    bundle.getString("organized_by"),
                    bundle.getString("start_date"),
                    bundle.getString("end_date"),
                    bundle.getString("start_hour"),
                    bundle.getString("end_hour"),
                    bundle.getString("location"),
                    bundle.getString("event_image"),
                    bundle.getString("user_id")
            )
        }
    }
    fun toBundle() : Bundle{
        val bundle = Bundle()
        with(bundle) {
            putInt("id", id)
            putString("name", name)
            putString("description", description)
            putString("organized_by", organizedBy)
            putString("start_date", startDate)
            putString("end_date", endDate)
            putString("start_hour", startHour)
            putString("end_hour", endHour)
            putString("location", location)
            putString("event_image", eventImage)
            putString("user_id", userId)
        }
        return bundle
    }
}