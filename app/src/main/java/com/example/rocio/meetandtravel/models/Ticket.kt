package com.example.rocio.meetandtravel.models

import android.os.Bundle
import com.google.gson.annotations.SerializedName

data class Ticket(
        val id: Int,
        val code: String? = "",
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("ticket_type")
        val ticketType: Ticket_Type){

    companion object {
        fun from(bundle: Bundle) : Ticket {
            return Ticket(
                    bundle.getInt("id"),
                    bundle.getString("code"),
                    bundle.getInt("user_id"),
                    Ticket_Type.from(bundle.getBundle("ticket_type"))
            )
        }
    }

    fun toBundle() : Bundle {
        val bundle = Bundle()
        with(bundle) {
            putInt("id", id)
            putString("code", code)
            putInt("user_id", userId)
            putBundle("ticket_type", ticketType.toBundle())
        }
        return bundle
    }

}
