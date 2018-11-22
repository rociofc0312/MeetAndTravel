package com.example.rocio.meetandtravel.models

import android.os.Bundle
import com.google.gson.annotations.SerializedName

data class TicketType(
        val id: Int,
        val name: String? = "",
        val stock: Int,
        val cost: Float,
        @SerializedName("event_id")
        val eventId: String? = null,
        val event: Event? = null){
    companion object {
        fun from(bundle: Bundle) : TicketType {
            return TicketType(
                    bundle.getInt("id"),
                    bundle.getString("name"),
                    bundle.getInt("stock"),
                    bundle.getFloat("cost"),
                    bundle.getString("event_id"),
                    Event.from(bundle.getBundle("event"))
            )
        }
    }
    fun toBundle() : Bundle {
        val bundle = Bundle()
        with(bundle) {
            putInt("id", id)
            putString("name", name)
            putInt("stock", stock)
            putFloat("cost", cost)
            putString("event_id", eventId)
            putBundle("event", event?.toBundle())
        }
        return bundle
    }

    override fun toString() : String{
        return name!!
    }
}