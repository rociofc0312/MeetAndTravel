package com.example.rocio.meetandtravel.models

import android.os.Bundle
import com.google.gson.annotations.SerializedName

data class User(
        val id: Int,
        val firstname: String? = "",
        val lastname: String? = "",
        val email:String? = "",
        val telephone:String? = "",
        val dni:String? = "",
        val birthdate:String? = "",
        val password:String? = "",
        @SerializedName("confirm_password")
        val confirmPassword: String? = "") {
    companion object {
        fun from(bundle: Bundle) : User {
            return User(
                    bundle.getInt("id"),
                    bundle.getString("firstname"),
                    bundle.getString("lastname"),
                    bundle.getString("email"),
                    bundle.getString("telephone"),
                    bundle.getString("dni"),
                    bundle.getString("birthdate")
            )
        }
    }
    fun toBundle() : Bundle {
        val bundle = Bundle()
        with(bundle) {
            putInt("id", id)
            putString("firstname", firstname)
            putString("lastname", lastname)
            putString("email", email)
            putString("telephone", telephone)
            putString("dni", dni)
            putString("birthdate", birthdate)
        }
        return bundle
    }
}