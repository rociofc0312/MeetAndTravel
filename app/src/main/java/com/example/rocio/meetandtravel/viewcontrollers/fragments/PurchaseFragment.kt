package com.example.rocio.meetandtravel.viewcontrollers.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.androidnetworking.error.ANError
import com.example.rocio.meetandtravel.R
import com.example.rocio.meetandtravel.models.Event
import com.example.rocio.meetandtravel.models.Preferences
import com.example.rocio.meetandtravel.models.TicketType
import com.example.rocio.meetandtravel.network.MeetAndTravelApi
import com.example.rocio.meetandtravel.network.NetworkResponse
import com.example.rocio.meetandtravel.viewcontrollers.activities.LoginActivity
import kotlinx.android.synthetic.main.fragment_purchase.*
import kotlinx.android.synthetic.main.fragment_purchase.view.*
import org.json.JSONArray
import org.json.JSONObject



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PurchaseFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PurchaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PurchaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null
    var event: Event? = null
    private var arrayTicketTypes = ArrayList<TicketType>()
    lateinit var ticketTypeSelected:String
    var prefs: Preferences? = null
    private var purchase = JSONArray()
    private val INVALID_LOGIN = 28800000
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_purchase, container, false)
        event = Event.from(arguments!!)
        prefs = Preferences(view.context)
        view.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                quantityTV.text = getString(R.string.number_of_tickets, i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        view.buttonPurchase.setOnClickListener{
            if(validateTime(prefs!!.time)){
                startActivity(Intent(view.context, LoginActivity::class.java))
            } else{
                MeetAndTravelApi.requestTicketTypesPurchase(buildEvent(), prefs!!.userToken!!, ticketTypeSelected, { response -> handleResponseApi(response) }, { error -> handleError(error)})
            }

        }

        return view
        }
    private fun validateTime(time: Long): Boolean{
        val currentTime = System.currentTimeMillis()
        Log.d(MeetAndTravelApi.tag, "Substract: ${currentTime - time}")
        return currentTime - time > INVALID_LOGIN
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MeetAndTravelApi.requestMyTicketTypesByEvent(event!!.id.toString(), { response -> handleResponse(view,response)},{ error ->handleError(error)})

    }
    private fun buildEvent(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("credit_card", "371449635398431")
        jsonObject.put("quantity", seekBar.progress)
        Log.d(MeetAndTravelApi.tag, jsonObject.toString())
        return jsonObject
    }
    private fun handleResponse(view: View,response: NetworkResponse?) {
        if ("error".equals(response!!.status, true)) {
            Log.d(MeetAndTravelApi.tag, response.message)
            return
        }
        arrayTicketTypes = response.ticket_types!!
        val spinner = view.findViewById<View>(R.id.spinner) as Spinner
        spinner.adapter = ArrayAdapter<TicketType>(activity, R.layout.support_simple_spinner_dropdown_item, arrayTicketTypes)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var objeto :TicketType = parent.selectedItem as TicketType
                ticketTypeSelected = objeto.id.toString()
                Log.d(MeetAndTravelApi.tag, ticketTypeSelected)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                /*Do something if nothing selected*/
            }
        }
        Log.d(MeetAndTravelApi.tag, "Found ${arrayTicketTypes.size} TicketTypes")

//        fun getSelectedTicketTypes(view : View){
//            var objeto :TicketType = spinner.selectedItem as TicketType
//            Log.d(MeetAndTravelApi.tag, objeto.name)
//        }
    }

    private fun handleError(anError: ANError?) {
        Log.d(tag, anError!!.message)
    }

    private fun handleResponseApi(response: NetworkResponse?) {
        Toast.makeText(context, response!!.message, Toast.LENGTH_LONG).show()
        Log.d(MeetAndTravelApi.tag, response!!.message)
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     *
//     *
//     * See the Android Training lesson [Communicating with Other Fragments]
//     * (http://developer.android.com/training/basics/fragments/communicating.html)
//     * for more information.
//     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PurchaseFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                PurchaseFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}
