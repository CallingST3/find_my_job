package com.apps.findmyjob.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.Navigation

import com.apps.findmyjob.R
import com.apps.findmyjob.util.APP_TAG
import com.apps.findmyjob.util.CitiesMap
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Search starting page
 */
class SearchFragment : Fragment() {

    private var userInput : String = ""
    private var region: String = "Москва"
    private val citiesMap = CitiesMap.cities

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        spinnerValueRegionSearchPage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                region = parent?.getItemAtPosition(position).toString()
                Log.d(APP_TAG, "spinner result: $region")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        buttonStartSearchPage.setOnClickListener{
            userInput = editTextUserInputSearchPage.text.toString().trim()
            val regionId = getRegionIdByName(region, citiesMap)
            if (userInput.isNotEmpty()) {
                navigateToListOfResults(it, userInput, regionId)
            }
            
        }

        buttonIconStartSearchPage.setOnClickListener{
            userInput = editTextUserInputSearchPage.text.toString().trim()
            val regionId = getRegionIdByName(region, citiesMap)
            if (userInput.isNotEmpty()) {
                navigateToListOfResults(it, userInput, regionId)
            }
        }

    }

    private fun navigateToListOfResults(v : View, userInput :String, regionId : String) {
        val action = SearchFragmentDirections.actionSearchFragmentToListOfResultsFragment()
        action.regionId = regionId
        action.userInputText = userInput
        Navigation.findNavController(v).navigate(action)
    }

    private fun getRegionIdByName(region :String, citiesMap : HashMap<String, String>) : String {
        val regionKey = citiesMap.filterKeys { it == region }.values
        return regionKey.min().toString()
    }

}
