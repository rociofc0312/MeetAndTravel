package com.example.rocio.meetandtravel.viewcontrollers.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Provider
import kotlinx.android.synthetic.main.item_provider.view.*

class AllProvidersAdapter(private val onProviderClickListener: OnProviderClickListener, var providers: List<Provider>, val context: Context):
        RecyclerView.Adapter<AllProvidersAdapter.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_provider, p0, false))
    }

    override fun getItemCount(): Int {
        return providers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val providers = providers.get(position)
        holder.updateFrom(providers)
    }

    interface OnProviderClickListener{
        fun onClick(provider: Provider)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val providerImageView = view.providerImageView
        val nameProviderTextView = view.nameProviderTextView
        val telephoneTextView = view.telephoneTextView
        val locationProviderTextView = view.locationProviderTextView
        val providerLayout = view.providerLayout

        fun updateFrom(provider: Provider){
            providerImageView.setDefaultImageResId(R.mipmap.ic_launcher)
            providerImageView.setErrorImageResId(R.mipmap.ic_launcher)
            providerImageView.setImageUrl(provider.providerImage)

            nameProviderTextView.text = provider.name
            telephoneTextView.text = provider.telephone
            locationProviderTextView.text = provider.location

        }
    }
}