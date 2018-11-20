package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Provider
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import kotlinx.android.synthetic.main.item_my_providers.view.*
import org.json.JSONArray
import org.json.JSONObject
import android.widget.CheckBox



class MyProvidersAdapter(var providers: List<Provider>, val context: Context): RecyclerView.Adapter<MyProvidersAdapter.ViewHolder>(){

    companion object {
        val checkedProviders = ArrayList<Int>()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_providers, p0, false))
    }

    override fun getItemCount(): Int {
        return providers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val provider = providers.get(p1)
        holder.updateFrom(provider)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val myProviderImageView = view.myProviderImageView
        val myProviderNameTextView = view.myProviderNameTextView
        val myProviderLocationTextView = view.myProviderLocationTextView
        val myProviderCheckBox = view.myProviderCheckBox
        val myProviderLayout = view.myProviderLayout

        fun updateFrom(myProvider: Provider){
            myProviderImageView.setDefaultImageResId(R.mipmap.ic_launcher)
            myProviderImageView.setErrorImageResId(R.mipmap.ic_launcher)
            myProviderImageView.setImageUrl(myProvider.providerImage)

            myProviderNameTextView.text = myProvider.name
            myProviderLocationTextView.text = myProvider.location

            myProviderLayout.setOnClickListener {
                myProviderCheckBox.isChecked = !myProviderCheckBox.isChecked

                if(myProviderCheckBox.isChecked){
                    checkedProviders.add(myProvider.id)
                } else {
                    checkedProviders.remove(myProvider.id)
                }
                Log.d(MeetAndTravelApi.tag, "Clicked: ${checkedProviders}")
            }
        }
    }
}