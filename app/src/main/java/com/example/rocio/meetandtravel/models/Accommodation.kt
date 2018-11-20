package com.example.rocio.meetandtravel.models

import android.os.Bundle
import com.google.gson.annotations.SerializedName

data class Accommodation(
        val id: Int,
        @SerializedName("reservation_code")
        val reservationCode: String? = "",
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("accommodation")
        val accommodationType: AccommodationType){

    companion object {
        fun from(bundle: Bundle) : Accommodation {
            return Accommodation(
                    bundle.getInt("id"),
                    bundle.getString("reservation_code"),
                    bundle.getInt("user_id"),
                    AccommodationType.from(bundle.getBundle("accommodation"))
            )
        }
    }

    fun toBundle() : Bundle {
        val bundle = Bundle()
        with(bundle) {
            putInt("id", id)
            putString("reservation_code", reservationCode)
            putInt("user_id", userId)
            putBundle("accommodation", accommodationType.toBundle())
        }
        return bundle
    }

}
