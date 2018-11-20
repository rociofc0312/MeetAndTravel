package com.example.rocio.meetandtravel.viewcontrollers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import javax.xml.transform.Source


class EventFragment : Fragment() {
    var event: Event? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        event = Event.from(arguments!!)
        Log.d(tag, event!!.eventImage)
        with(view.eventImageView){
            setDefaultImageResId(R.mipmap.ic_launcher)
            setErrorImageResId(R.mipmap.ic_launcher)
            setImageUrl(event!!.eventImage)
        }
        view.startDateEventTextView.text = event!!.startDate
        view.startHourEventTextView.text = event!!.startHour
        view.endDateEventTextView.text = event!!.endDate
        view.endHourEventTextView.text = event!!.endHour
        view.companyEventTextView.text = event!!.description
        view.nameEventTextView.text = event!!.name
        view.detailEventTextView.text = event!!.organizedBy
        view.addressEventTextView.text = event!!.location

        view.seeProvidersButton.setOnClickListener{
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            val providersFragment = ProvidersFragment()
            providersFragment.arguments = event!!.toBundle()
            Log.d(tag, arguments.toString())
            fragmentTransaction.replace(R.id.mainContent, providersFragment)
            fragmentTransaction.addToBackStack("Providers")
            fragmentTransaction.commit()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "Detalle Evento"
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
